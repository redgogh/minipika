package org.poseidon.experiment;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on TODO DATE
 */

import org.raniaia.poseidon.framework.provide.model.Column;
import org.raniaia.poseidon.framework.provide.model.Comment;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.framework.provide.model.PK;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright: Create by tiansheng on 2019/12/9 10:35
 */
@Data
@Model("wallet")
public class WalletModel {

    @PK
    @Column("int(11) not null")
    private Long id;

    @Column("decimal(10,3) not null")
    @Comment("金额")
    private BigDecimal amount;

}
