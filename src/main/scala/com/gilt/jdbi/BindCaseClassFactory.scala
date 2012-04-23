package com.gilt.jdbi

import org.skife.jdbi.v2.SQLStatement
import org.skife.jdbi.v2.sqlobject.{Binder,BinderFactory}
import java.lang.annotation.Annotation

class BindCaseClassFactory extends BinderFactory {

  def build(annotation: Annotation) = new Binder[BindCaseClass, Product] {
    override def bind(q: SQLStatement[_], bind: BindCaseClass, arg: Product) {
      val prefix =
        if (bind.value == "___jdbi_bare___") ""
        else bind.value + "."

      val fields = arg.getClass.getDeclaredFields

      val methods = arg.getClass.getDeclaredMethods
                      .filter { _.getParameterTypes.isEmpty }
                      .map { m => m.getName -> m }.toMap

      for (field <- fields) {
        val fieldName = field.getName

        // If there is a method and a field of the same name,
        // prefer the result of the method.
        val methodOpt = methods.get(fieldName)
        val scalaFieldValue = methodOpt.map { _.invoke(arg) }.getOrElse(field.get(arg))

        // If the result is an Option, unwrap it.
        val fieldValue = scalaFieldValue match {
          case None    => null
          case Some(x) => x
          case x       => x
        }

        q.bind(prefix + fieldName, fieldValue)
      }

    }

  }

}
