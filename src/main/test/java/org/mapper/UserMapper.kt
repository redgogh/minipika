package org.mapper

import org.poseidon.experiment.UserInfo
import org.raniaia.poseidon.framework.provide.SQL

interface UserMapper{

    fun queryUserById(): UserInfo

}