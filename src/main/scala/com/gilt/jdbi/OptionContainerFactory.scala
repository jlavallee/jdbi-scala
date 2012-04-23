package com.gilt.jdbi

import java.sql.ResultSet
import org.skife.jdbi.v2.ContainerBuilder
import org.skife.jdbi.v2.tweak.ContainerFactory

/**
 * Wraps the results of a JDBI query in an Option.
 *
 * When setting up your DBI instance, register this factory:
 *
 *    db.registerContainerFactory(new OptionContainerFactory)
 *
 * Then you can specify an Option type as the return for you
 * SqlQuery. Note that you must also tell JDBI the contained
 * type using @SingleValueResult:
 *
 *    @SqlQuery("...")
 *    @SingleValueResult(classOf[DooHickey])
 *    def findByKey(@Bind("key") key: String): Option[DooHickey]
 *
 */
class OptionContainerFactory extends ContainerFactory[Option[_]] {

  override def accepts(clazz: Class[_]) = classOf[Option[_]].isAssignableFrom(clazz)

  override def newContainerBuilderFor(clazz: Class[_]) = new OptionContainerBuilder

  class OptionContainerBuilder extends ContainerBuilder[Option[_]] {
    private var opt: Option[_] = None

    override def add(it: AnyRef) = {
      opt = Some(it)
      this
    }

    override def build = opt
  }
}
