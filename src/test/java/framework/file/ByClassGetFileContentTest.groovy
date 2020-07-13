package framework.file

import test.MqlMapper
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 * Creates on 2020/6/27.
 */

/**
 * @author tiansheng
 */
class ByClassGetFileContentTest {

  @Test
  void test() {
    def url = MqlMapper.classLoader.getResource("groovy/MqlMapper.class")
    println new String(Files.readAllBytes(Paths.get(url.toURI())))
  }

}
