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

/**
 * Create on 2020/3/20 0:04.
 *
 * <b>模组所在的包，每个modules包下的子包都必须包含一个core包以及
 * 提供一个暴露在外部的接口。</b>
 *
 * 暴露出去的接口可以理解为适配器，就是当有其他需求的时候，可以只需要通过跟换
 * 接口的实现即可，不必去更新其他的代码减少不必要的麻烦、并且后期容易维护。
 *
 * @author tiansheng
 */
package org.raniaia.poseidon.framework.context.component;