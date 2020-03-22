package org

import org.poseidon.experiment.UserModel
import org.raniaia.poseidon.framework.provide.SQL

interface SQLAnnotationKt {

    fun queryUserByUsername(): List<UserModel>

}