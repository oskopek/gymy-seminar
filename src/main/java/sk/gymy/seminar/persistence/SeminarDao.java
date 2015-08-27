/*
 * Copyright 2014 Ondrej Skopek
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package sk.gymy.seminar.persistence;

import org.optaplanner.examples.common.persistence.XStreamSolutionDao;
import sk.gymy.seminar.domain.Groups;

public class SeminarDao extends XStreamSolutionDao {

    public static final String dataDirName = "seminar";

    public SeminarDao() {
        super(dataDirName, Groups.class);
    }

}
