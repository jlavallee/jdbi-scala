package com.gilt.jdbi
import org.skife.jdbi.v2.TransactionCallback
import org.skife.jdbi.v2.Handle
import org.skife.jdbi.v2.TransactionStatus

object Conversions {

  /**
   * Convenience feature to run a function literal in a transaction.
   *
   * Typical usage:
   *
   *     db.inTransaction(
   *       (h: Handle, status: TransactionStatus) => {
   *         // ...
   *       }
   *     )
   *
   */
  implicit def function2TransactionCallback[T](f: (Handle, TransactionStatus) => T): TransactionCallback[T] =
    new TransactionCallback[T] {
      def inTransaction(h: Handle, s: TransactionStatus) = f(h,s)
    }

}
