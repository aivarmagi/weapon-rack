--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements

INSERT INTO WeaponRack (id, capacity, name) VALUES ('f63e7951-d087-4bf1-b0ca-d82570b3ff0c', 5, 'rack1');
INSERT INTO WeaponRack (id, capacity, name) VALUES ('a1fce73a-51d9-459d-a047-b4e3cecd9c43', 4, 'rack2');
INSERT INTO WeaponRack (id, capacity, name) VALUES ('00ab79cf-a8b5-4831-8c40-ae65c4a66413', 3, 'rack3');
INSERT INTO WeaponRack (id, capacity, name) VALUES ('91b94fd9-7126-400c-a234-afb65d1a31be', 2, 'rack4');

INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('dbdfa0db-82c7-41d9-809b-277054330b04', 'f63e7951-d087-4bf1-b0ca-d82570b3ff0c', 'M4A1', '5.56', 3510, NOW()); --rack1
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('1ec99fd1-94e0-478e-bd33-45cf3ea4aad4', 'f63e7951-d087-4bf1-b0ca-d82570b3ff0c', 'Steyr AUG', '5.56', 3600, NOW()); --rack1
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('ee670ee9-4807-40b9-85f3-d16a30f69bfd', 'f63e7951-d087-4bf1-b0ca-d82570b3ff0c', 'FAMAS', '5.56', 3610, NOW()); --rack1
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('f2f5a5bd-707a-4788-8bba-e81c7dc5b594', '00ab79cf-a8b5-4831-8c40-ae65c4a66413', 'IMI Galil', '5.56', 3950, NOW()); --rack3
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('6748990c-73fc-4b1b-93c1-476319b30559', '00ab79cf-a8b5-4831-8c40-ae65c4a66413', 'SIG SG 552', '5.56', 3200, NOW()); --rack3
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('535f27ce-e137-4362-8c48-e5fcd3ecb146', '91b94fd9-7126-400c-a234-afb65d1a31be', 'AK-47', '7.62', 4780, NOW()); --rack4
INSERT INTO Weapon (id, weapon_rack_id, name, caliber, weight, put_time) VALUES ('28372b9b-dbed-4ee0-bddc-f4d6eb70fb28', '91b94fd9-7126-400c-a234-afb65d1a31be', 'SVD', '7.62', 4300, NOW()); --rack4
