-- Delete previous data
DELETE
FROM player_roles;
DELETE
FROM rpchars;
DELETE
FROM players;
DELETE
FROM faction_claimed_regions;
DELETE
FROM claimbuild_special_buildings;
DELETE
FROM production_claimbuild;
DELETE
FROM claimbuilds;

-- Reset id counters
ALTER SEQUENCE players_id_seq RESTART WITH 1;
ALTER SEQUENCE rpchars_id_seq RESTART WITH 1;
ALTER SEQUENCE claimbuilds_id_seq RESTART WITH 1;

-- Players
INSERT INTO players (discord_id, ign, uuid, faction)
VALUES ('261173268365443074', 'Luktronic', 'cefabe13fda44d378c5d7292724f1514', 21),
       ('244463773052567553', 'mirak441', '4cd6b222b3894fd59d85ac90aa2c2c46', 21),
       ('117447972710514693', 'Glupe1', '52a396a38b92412f88b43c00c56d833f', 22),
       ('187040691564576768', 'Hamvogar', '211f84977011456c8609b4ccd4cdfcc1', 24),
       ('386234827319410691', 'AnoJedt', '2bf56f1b870742dbb0025706a667d8c3', 21),
       ('223821939972636682', 'jvanhierden', '8368dcfe90184d39abca98f8f1cdbeef', 21),
       ('219498915236413441', 'WaffleEcsDee', '6dc9ac6e79fb41ef87687e448e9993bf', 27),
       ('326492361460809729', 'SebThePro200', '1c685bc10097492aa5bce90514f64d98', 23),
       ('465945325815988246', 'Halt03', '7956eba887af4c0795cd4e926c35498b', 19),
       ('302411752463663104', 'SilberElb', '69be1bd412114edeb296a1209cd4b869', 21),
       ('253505646190657537', 'VernonRoche', '866830b12e944a97918439282412c487', 8),
       ('305738671603056640', 'MrClasster', 'd63e33bb7d3b484a88388de63e4d58fb', 28),
       ('619647911810039819', 'OP_apples', '6cb74cbeb08f4a56b05aa03d74cef045', 2),
       ('576542325409579009', 'Actor_chan', 'c76275119358409082693213d1a5db42', 5),
       ('287652368538140672', 'bakbar', '83c5b1294daa4497baafeb582288e214', 7),
       ('356314507439636490', 'Not_Pokerino', '2fff454e80c848b7890c88e2ceebe823', 22),
       ('653941599566430229', 'ElectricSolar', '3e9c1f883af042659b201aa90f2a04e5', 19),
       ('332800094808178688', 'Anedhel', 'ae7219bf4e444bc4b7fc3c74e95786f0', 21),
       ('238713618982633472', 'nodas1995', '880eff8947ce4c5eb89fc0daa80a25d0', 15),
       ('199960180585136128', 'AustriaBear', '80bbc8990987467aab40d111831e7d5f', 21),
       ('323522559096258582', 'HabKeinTeammate', '84b6a14958ec4b2bb9b479328526651d', 21),
       ('541263026490179594', 'bogifighter', '7c6dda4edea34deba125050b81875e70', 21),
       ('256693291301601280', 'Estionese', 'a3590a4fd7fc40248fb6f27fcc7168f3', 18),
       ('451378765885472768', 'DaMoffe', '899880c229ec48259a83442c3e7bb4e9', 23),
       ('614043370838425616', 'Wer_ist_das_mp4', '9f3eea03676744a188405174c93a0e9e', 21),
       ('131641969523949568', 'brackman', 'bfbff2d2bb72496197b3b0e5cf447795', 17),
       ('506759496916533269', 'VO_Kop', '5cc9d6505cfb4b20a87e6d4f61a6e523', 30),
       ('615768113933320215', 'Ilyra_', 'c2fec28a14f14ec1a05e92d2f425b946', 19),
       ('107902800804982784', 'PenguLV', 'cb288fc014d94a8b91c756d8c86fb674', 20),
       ('239805577906094081', 'Vaquinux_', '8b3d1fdce0f94cc19b18d14d2cec4b34', 19),
       ('694970298738540624', 'Fidnas', '0c5730ee40d64b3d85163e013f7908b1', 2),
       ('257132043895308288', 'Brego_16', '26f2912fabaf41169ffdd731733e8f2b', 29),
       ('329658238566137867', 'PersephoNeko', '26a909c030084ac192a68ff40a3891ab', 17),
       ('290251857928912898', 'ShadowsEmissary1', '6c67b630882746faad33cc445728d6a1', 27),
       ('530482795126980608', 'ten7u', 'f1c417c0d43049478a99b6c8bf21d991', 20),
       ('242342469339971584', 'EpicKiller113', '8aae1dfde284403c88fc50e72c08e5a2', 20),
       ('404564519579418634', 'HellSpawn75', '91ffd49997b8467b81bc6ff45a69374a', 12),
       ('230027896805130240', 'incorrectkiwi', 'e2ca1869a78042f09d28dc3d377bf6fc', 14),
       ('358320428122505217', 'Linnis33', '5a3d51bcfb9445de8847102c4663a079', 25),
       ('321680076422905856', 'SirKai', '7fdeee481a1540b2aa015c5b6a29f005', 15),
       ('573932552742764569', 'TheMarshMan131', '89997528b20c441ba0ae0b516c34a9fd', 19),
       ('893622681155559425', 'Anummindor', '65697ce197da4fc9b675b055eac31928', 27),
       ('510633380136615956', 'Micro_Tiny', '7a1043cadf544c9d897824a3dffe3dcd', 20),
       ('787072288817807360', 'Duckygamer86', 'fa6afd7cb7f545409b0c74009e526a1a', 16),
       ('241070228312817666', 'SirMath', 'fe6bdb2a180948bb89c09ed24d9ca072', 21),
       ('249581174161145857', 'Juststan147', 'ec5ee63695ce49e28caed44eba877915', 21),
       ('266951038014193667', 'PrinzAtago', '5519d591cb1f497a87c9df44306624b9', 6),
       ('1029843811330965544', 'NanoMCraft', '82a70d42f1f14599ba7c1f310b1aeb99', 28),
       ('184287052655755264', 'NietMaxim', '43763c53ea1648d0a7e91372b8548fe9', 21),
       ('906332631724081172', 'Jesteur', '4769352bb56043b783f6e44f05af1c88', 27),
       ('249986430892376065', 'me_is_hidden', '939c062bbb22492a9ec5edb450d1708f', 23),
       ('518780310071148554', 'TutsMaNix', '687a491e72794731aefd288920b9fe43', 9),
       ('234035066227916800', 'Thallahhh', '6051bd52baf3495483da0f3c7653fcbc', 14),
       ('750411451298218094', 'jadencad', '0d397008851249eb8986e8105a287261', 16),
       ('611388827201830912', 'Ultatrog', '3692197228c84eb7a28a8f4446ed37cb', 3),
       ('271437853639049216', 'NineteenLetters', 'ef6ff8a4d66f4982b66851e255a39291', 4),
       ('657627745253064724', 'TheSm1ler', 'c8fea02402044dfda938584b6475230f', 17),
       ('233686550477537280', 'enlists', 'c56fcc22888e4982b904e30e55f97ca9', 19),
       ('391662269962125328', 'HanslaRoi', '370de6d975404eae967d0edddb2dee7f', 19),
       ('692830733428850710', 'Xnoodledoodle', '77d1843db7af4c3aaf9967e2f6e6588a', 17),
       ('332577121920745472', 'kismucek', 'd160f49bd9de4f7187953808fa4bb29e', 27),
       ('349248375763370004', 'LordAmarok', 'bb1593fed018438986e5cfe6420c33fa', 30),
       ('440212838368411648', 'DangoR12', '8b5ca1f46b1c4dad91d7b3ecbde196a0', 25),
       ('680103992227004470', 'tomynick', '6da7c58c04d940fe9bcda4ce1d533ea8', 25),
       ('286477683997671424', 'MartijnH2001', 'c861b31616494b85af800bf68b83bc57', 26),
       ('333592886148857857', 'TheBozmeister', 'bfbd8b7cc0584787a830865551186fa6', 20),
       ('866400719598780437', 'Dewesto', 'bfaf8b3e166d448593b8500edb93af02', 5),
       ('187286736664002561', 'Mikaelix', 'f8bcda2c2c4b4d859a3f04768973ebdf', 11),
       ('607152840309342209', 'NuclearGuy101', 'b0f6f3124d854e71b64935a4f2569ff3', 21),
       ('612706637983121423', 'D4RK_CAKE', 'cdf0c90695cf4bdf9cffabbd685335b8', 30),
       ('881101535747117086', 'Narkr3', 'caf45bc06ed54d1482995ae5cdf38d73', 26),
       ('778293873360764948', 'gemeinerschatten', 'ea46f882bef04b4f90a974caf27d2ec9', 25),
       ('579650565571608603', 'Nazouvak', 'f251ce7d43664353aa46382a9e566ce4', 23),
       ('463054258980388874', 'Carovny_fila_01', '8df32ed1d4b24491ac9b4037ed260155', 25),
       ('393523067135197185', 'xanyy_', '086d907c7a5e445694224cfd47a0964d', 25),
       ('997192744407674890', 'MacIsCheesy', '751401cc9f3d46758a2dd6efd061d1c0', 10),
       ('340075954980519936', 'aggiroo', '83a28db0fc7b4fdcb8c24af896c1bda5', 17),
       ('201031579370258432', 'Bornholmeren', '54b0ffa42c744796957a87a6a76037de', 27),
       ('323992487116931074', 'Joruin', 'a26c0a00676249e9858a22915403c1d1', 24),
       ('724306879320424449', 'TheTrueBlueBot', 'db9522e85d79439d9990cdfb3aacaeb8', 19),
       ('524394862074920960', 'Divination_Magic', '9a6b82810b534a0b99575410ee1c0b80', 2),
       ('660016967113244683', 'Pilzwigder17te', '87a14c654a8143dbb1ac9bff38c34eef', 21),
       ('1048267036746330123', 'DDRC', '69a93fd07fe44cbeb8d62aed13da7108', 26),
       ('1028986087768596500', 'XxLegendOYASUOxX', '9f53ef4b22844ef8adaff9da1999c7c9', 24),
       ('351279368930197505', 'Gyml', '7f9042aeb51f47bd82a76afa494a9dcc', 2),
       ('332516348112535560', 'Jqlo', '7e15950f8087424c9d86ccc7235cac46', 27),
       ('342980227120496640', 'BartOV', 'e8ee45e0b4f246bf8654ddc6ce8a31da', 19),
       ('526787243420155905', 'BasilCrystalfig', '4e5c1100e6ee489d9894144a5d56cc45', 19),
       ('287598789626167316', 'joestoen', '79f173b9bedb4792afcc75a30e6de835', 28),
       ('546775912499118080', 'ZukiPhantasmal', 'a946b3cbd36d444494ad39af740b6573', 3),
       ('763680695495163954', 'AinsOaaGown', '2d7ea7fa395949d182e357e440150aaf', 30),
       ('1073882499865915452', 'DaemonTruefyre', 'cd5c399f8ece484f85a9d8157ad6da3f', 27),
       ('231741217417723904', 'Jukoz', 'd3edcfa0dc004449b218f70bafd62e8b', 27),
       ('378608926444224515', 'ag3ntcrab', '7355a9e51c704b7f9e29b8c5d92e0999', 27),
       ('314708489576382464', 'xHenkka', 'b09133e908f24071b2d9535247b1c7a6', 27);



-- Add player roles
INSERT INTO player_roles(player_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER'),
       (6, 'ROLE_USER'),
       (7, 'ROLE_USER'),
       (8, 'ROLE_USER'),
       (9, 'ROLE_USER'),
       (10, 'ROLE_USER'),
       (11, 'ROLE_USER'),
       (12, 'ROLE_USER'),
       (13, 'ROLE_USER'),
       (14, 'ROLE_USER'),
       (15, 'ROLE_USER'),
       (16, 'ROLE_USER'),
       (17, 'ROLE_USER'),
       (18, 'ROLE_USER'),
       (19, 'ROLE_USER'),
       (20, 'ROLE_USER'),
       (21, 'ROLE_USER'),
       (22, 'ROLE_USER'),
       (23, 'ROLE_USER'),
       (24, 'ROLE_USER'),
       (25, 'ROLE_USER'),
       (26, 'ROLE_USER'),
       (27, 'ROLE_USER'),
       (28, 'ROLE_USER'),
       (29, 'ROLE_USER'),
       (30, 'ROLE_USER'),
       (31, 'ROLE_USER'),
       (32, 'ROLE_USER'),
       (33, 'ROLE_USER'),
       (34, 'ROLE_USER'),
       (35, 'ROLE_USER'),
       (36, 'ROLE_USER'),
       (37, 'ROLE_USER'),
       (38, 'ROLE_USER'),
       (39, 'ROLE_USER'),
       (40, 'ROLE_USER'),
       (41, 'ROLE_USER'),
       (42, 'ROLE_USER'),
       (43, 'ROLE_USER'),
       (44, 'ROLE_USER'),
       (45, 'ROLE_USER'),
       (46, 'ROLE_USER'),
       (47, 'ROLE_USER'),
       (48, 'ROLE_USER'),
       (49, 'ROLE_USER'),
       (50, 'ROLE_USER'),
       (51, 'ROLE_USER'),
       (52, 'ROLE_USER'),
       (53, 'ROLE_USER'),
       (54, 'ROLE_USER'),
       (55, 'ROLE_USER'),
       (56, 'ROLE_USER'),
       (57, 'ROLE_USER'),
       (58, 'ROLE_USER'),
       (59, 'ROLE_USER'),
       (60, 'ROLE_USER'),
       (61, 'ROLE_USER'),
       (62, 'ROLE_USER'),
       (63, 'ROLE_USER'),
       (64, 'ROLE_USER'),
       (65, 'ROLE_USER'),
       (66, 'ROLE_USER'),
       (67, 'ROLE_USER'),
       (68, 'ROLE_USER'),
       (69, 'ROLE_USER'),
       (70, 'ROLE_USER'),
       (71, 'ROLE_USER'),
       (72, 'ROLE_USER'),
       (73, 'ROLE_USER'),
       (74, 'ROLE_USER'),
       (75, 'ROLE_USER'),
       (76, 'ROLE_USER'),
       (77, 'ROLE_USER'),
       (78, 'ROLE_USER'),
       (79, 'ROLE_USER'),
       (80, 'ROLE_USER'),
       (81, 'ROLE_USER'),
       (82, 'ROLE_USER'),
       (83, 'ROLE_USER'),
       (84, 'ROLE_USER'),
       (85, 'ROLE_USER'),
       (86, 'ROLE_USER'),
       (87, 'ROLE_USER'),
       (88, 'ROLE_USER'),
       (89, 'ROLE_USER'),
       (90, 'ROLE_USER'),
       (91, 'ROLE_USER'),
       (92, 'ROLE_USER'),
       (93, 'ROLE_USER'),
       (94, 'ROLE_USER'),
       (95, 'ROLE_USER');


-- Add faction claimed regions
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('148', 1);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('332', 2);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('331', 2);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('333', 2);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('165', 3);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('211', 4);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('135', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('136', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('200', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('196', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('173', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('176', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('174', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('192', 5);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('440', 6);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('41', 7);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('17', 7);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('391', 8);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('184', 9);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('121', 10);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('119', 10);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('57', 10);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('168', 11);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('460', 12);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('441', 12);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('459', 12);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('461', 12);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('110', 13);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('426', 14);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('430', 14);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('431', 14);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('447', 14);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('557', 15);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('546', 15);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('146', 16);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('495', 18);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('494', 18);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('151', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('74', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('38', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('77', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('102', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('150', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('76', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('40', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('101', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('103', 19);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('346', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('395', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('397', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('398', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('392', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('390', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('425', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('341', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('342', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('424', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('443', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('343', 20);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('336', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('313', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('325', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('263', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('335', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('261', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('311', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('330', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('339', 21);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('407', 22);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('420', 22);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('400', 22);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('408', 22);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('51', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('50', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('61', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('60', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('63', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('53', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('62', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('52', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('44', 23);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('201', 24);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('246', 24);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('245', 24);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('111', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('71', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('113', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('134', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('126', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('72', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('178', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('69', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('109', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('114', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('68', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('70', 25);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('171', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('197', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('198', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('199', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('172', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('202', 26);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('266', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('240', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('239', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('265', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('345', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('269', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('295', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('209', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('235', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('237', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('236', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('267', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('205', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('309', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('305', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('264', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('302', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('203', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('303', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('310', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('298', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('297', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('206', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('301', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('204', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('306', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('241', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('307', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('207', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('238', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('208', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('242', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('300', 27);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('214', 28);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('215', 28);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('128', 29);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('130', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('180', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('129', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('175', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('132', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('131', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('133', 30);
INSERT INTO faction_claimed_regions (region, faction)
VALUES ('177', 30);


-- Add claimbuilds
INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Bree Starter Hamlet', 16785, 100, 1685, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 128, 1, 29, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rohan Starter Hamlet', 49956, 100, 54807, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 245, 2, 24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Southron Coast Starter Hamlet', 47174, 100, 138338, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 426, 3,
        14, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dale Starter Hamlet', 92019, 100, -9707, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 148, 4, 1, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rangers Starter Hamlet', 14340, 100, 11942, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 131, 5, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dorwinion Starter Hamlet', 119362, 100, 25384, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 211, 6, 4, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dol Guldur Starter Hamlet', 66585, 100, 66649, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 165, 7, 3, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dunland Starter Hamlet', 34520, 100, 27885, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 174, 8, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gondor Starter Hamlet', 78324, 100, 66198, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 263, 9, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gulf Starter Hamlet', 107540, 100, 153283, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 440, 10, 6, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gundabad Starter Hamlet', 49745, 100, -13638, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 41, 11, 7, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Half-Troll Starter Hamlet', 139551, 100, 217831, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 494, 12, 18,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Harnennor Starter Hamlet', 92896, 100, 106142, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 391, 13, 8,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lindon Starter Hamlet', -17337, 100, -17251, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 121, 14, 10, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lothlórien Starter Hamlet', 50419, 100, 19242, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 168, 15, 11,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Mordor Starter Hamlet', 95071, 100, 59469, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 267, 16, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Morwaith Starter Hamlet', 130761, 100, 187284, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 461, 17, 12,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Nomad Starter Hamlet', 84768, 100, 130849, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 400, 18, 22, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rhúdel Starter Hamlet', 136907, 100, 31557, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 215, 19, 28, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Angmar Starter Hamlet', 25489, 100, -27917, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 68, 20, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Woodland Realm Starter Hamlet', 77708, 100, -11631, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 146, 21,
        16, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rhunur-Khanur', 140179, 78, 74529, 0, 0, '4 small houses', 'none', 'Bartender', 'VILLAGE', 235, 22, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Tharnost', 78318, 100, 69024, 2, 1, '75 small Houses, 8 large houses, 2 Mansions', 'none', 'Missing',
        'CAPITAL', 311, 23, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Durins Folk Starter Hamlet', 101914, 100, -13903, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 102, 24,
        19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ered Luin Starter Hamlet', -27107, 100, -24996, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 52, 25, 23,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dol Amroth Starter Hamlet', 44901, 100, 77168, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 332, 26, 2,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Taurethrim Starter Hamlet', 67117, 100, 269417, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 546, 27, 15,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rivendell Starter Hamlet', 46266, 100, -2291, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 110, 28, 13,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Hobbit Starter Hamlet', -738, 100, 558, 0, 0, '4 Small Houses', 'None', 'None', 'HAMLET', 184, 29, 9, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Arvee', 49118, 69, 75460, 0, 0, '8 Small Houses', 'none', 'Gondor Bartender', 'VILLAGE', 331, 30, 2, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Isengard Starter Hamlet', 37542, 100, 42608, 0, 0, '4', 'None', 'None', 'HAMLET', 197, 31, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Umbar Starter Hamlet', 37542, 100, 42608, 0, 0, '4', 'None', 'None', 'HAMLET', 395, 32, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Varsa', 68680, 150, 20440, 1, 1, '16 Small Houses, 4 Large Houses', 'none',
        'Bartender:1-Smith:1-Dol Guldur Trader:1-Dol Guldur Commander:1-Oddmant collector:1', 'TOWN', 165, 33, 3, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ost Apa Ruin', 37450, 150, 2861, 1, 1, '18 Small Houses, 4 Large Houses', 'none', 'Bartender', 'TOWN', 131, 34,
        30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dragonic Castle', 106013, 150, 184896, 1, 0, 'none', 'none', 'Blacksmith', 'CASTLE', 460, 35, 12, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fána Ondo', 48048, 200, -1770, 0, 0, '12', 'None', 'Bartender', 'VILLAGE', 110, 36, 13, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Thingarholm', 103666, 150, -13566, 1, 1, '120 Small Houses, 4 Large Houses', 'none', 'Bartender:1-Market:4',
        'TOWN', 102, 37, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Cesu Castle', 49458, 82, 123487, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 397, 38, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Anfalindros', 46830, 70, 72186, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 325, 39, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Carach Morgul', 120693, 117, 54285, 1, 0, '4 small', 'none', 'Blacksmith', 'STRONGHOLD', 236, 40, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kala Garman', 94250, 157, 56155, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 237, 41, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Urdin-Bar', 93176, 82, 51600, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 209, 42, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Urdin-Dun', 128587, 123, 79956, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 295, 43, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Irdin Udun', 121099, 72, 54789, 1, 0, 'none', 'none', 'Blacksmith', 'CASTLE', 269, 44, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Priye-kuli', 57864, 71, 128826, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 398, 45, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Swanster Village', 44843, 69, 76900, 0, 0, '8 small houses', 'none', 'Bartender, Blacksmith', 'VILLAGE', 332,
        46, 2, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Magh Vadok', 87498, 70, 56408, 1, 0, '4', 'none', 'Blacksmith', 'STRONGHOLD', 266, 47, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Val-Myera', 68223, 64, 106126, 0, 0, '8small', 'none', 'Bartender', 'VILLAGE', 392, 48, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Sheepstroke Village', 104762, 77, 136734, 0, 0, '8small', 'none', 'Bartender', 'VILLAGE', 408, 49, 22, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Sarahan', 106268, 100, 137918, 1, 1, '16 small houses, 8 large houses', 'none', 'Bartender, oddment collector',
        'TOWN', 420, 50, 22, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gimog-Um', 84701, 78, 64778, 0, 0, '8 small', 'None', 'Dunlending Bartender', 'VILLAGE', 265, 51, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Cee Gulda', 99010, 69, 110511, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 390, 52, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Altak', 139073, 104, 23804, 1, 1, '24', 'none', 'Oddment collector', 'TOWN', 215, 53, 28, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Polis Hermanis', 49148, 75, 76784, 0, 0, '8 Small Houses', 'None', 'Bartender', 'VILLAGE', 333, 54, 2, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Potlood', 101332, 85, -12830, 0, 0, '12', 'none', 'Bartender', 'VILLAGE', 103, 55, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Knurlnien', -26357, 89, -21002, 1, 1, '21', 'none', 'Bartender, Oddment Collector', 'TOWN', 61, 56, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Anarthel', 73210, 72, 67176, 0, 0, '8 Small Houses', 'None', 'Bartender', 'VILLAGE', 313, 57, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lebenamath', 61697, 71, 78518, 0, 0, '8', 'None', 'Bartender', 'VILLAGE', 335, 58, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rithel', 56354, 72, 71867, 0, 0, '8 Small Houses', 'None', 'Bartender', 'VILLAGE', 330, 59, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Laktam Pahkah', 56850, 70, 264950, 0, 0, 'none', 'none', 'Commander, Smith', 'CASTLE', 557, 60, 15, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Shiverclank', 49749, 134, -23902, 0, 0, '8', 'none', 'Bartender', 'VILLAGE', 17, 61, 7, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Domrum', -27811, 80, -13169, 0, 0, '8 small Houses', 'none', 'Bartender, Blacksmith', 'VILLAGE', 60, 62, 23,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Hogh Turum', -22805, 80, -25261, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 63, 63, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kor Dorul', -28353, 80, -21337, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 53, 64, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Balbuldor', -20421, 80, -7558, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 62, 65, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kála-murg', 134366, 84, 230375, 1, 1, '16 small Houses, 4 large Houses', 'none',
        'Bartender, Oddment Collector', 'TOWN', 495, 66, 18, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Dur', 37575, 79, -15740, 0, 0, '8 small', 'None', 'Bartender', 'VILLAGE', 69, 67, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ethirië', 59618, 64, 81675, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 339, 68, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Castle Eron da Nikaer', 81900, 100, -19101, 1, 0, 'none', 'none', 'commander, smith', 'CASTLE', 76, 69, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kazlokroz', 84827, 80, 46224, 1, 1, '16 small Houses, 4 large Houses', 'none', 'Bartender, Oddment Collector',
        'TOWN', 239, 70, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Wulfs Deep', 34920, 80, 46224, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 196, 71, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gazān nAtunârī', 104327, 87, 134382, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 407, 72, 22, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Castle Nir Mountainth', 57459, 76, -21004, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 74, 73, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dav-Gav Castle', 46136, 76, 141751, 1, 1, '16 small 4 large', 'none', 'Bartender, Oddment collector', 'TOWN',
        425, 74, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('See Gulda', 67349, 70, 105253, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 341, 75, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('gaave ro fundup', 37440, 70, 43458, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 197, 76, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Carn Gorthaur', 35524, 71, -1823, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 113, 77, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Oo gre', 78728, 85, 84300, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 342, 78, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ravenville', 41541, 64, 35399, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 173, 79, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Strav-Upe', 99124, 72, 110546, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 346, 80, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('A Morgul Ships Rest', 82816, 66, 64681, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 240, 81, 27,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Liepāja', 49312, 64, 161269, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 424, 82, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lashkarg', 67718, 70, 37987, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 208, 83, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Drutot', 86990, 69, 54745, 1, 1, '16 small Houses, 4 large Houses', 'none',
        'Bartender, Oddment Collector, Commander', 'TOWN', 238, 84, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kehlgate', 46268, 72, 16346, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 136, 85, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Jhaun', 36000, 72, -1955, 0, 0, '8 small House', 'none', 'Bartender', 'VILLAGE', 111, 86, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('City of Shadows', 100465, 150, 60946, 2, 1, '194 Small Houses, 8 Large Houses, 2 Mansions', 'none',
        'Mordor Orc Trader:2-Wicked Dwarf Traders:2-Eastling Fishmonger:1-Eastling Goldsmith:1-Bartender:1', 'CAPITAL',
        267, 87, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dâr Mauzur', 71943, 72, 49623, 1, 0, 'none', 'none', 'Commander, Smith', 'CASTLE', 204, 88, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Búrz Duglub', 77355, 64, 51171, 1, 0, 'none', 'none', 'commander, smith', 'CASTLE', 206, 89, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Agarth', 49900, 83, -3190, 0, 0, '8 small', 'none', 'bartender', 'VILLAGE', 109, 90, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fruit & Wood village', 121541, 71, 74527, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 297, 91, 27,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ijukukelukavein', 40958, 98, 42147, 0, 0, '8 small Houses', 'None', 'Bartender', 'VILLAGE', 198, 92, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andros Watch', 79837, 66, 61782, 1, 0, 'none', 'None', 'Smith, Commander', 'CASTLE', 264, 93, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Urdun-Idun', 101559, 88, -16662, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 77, 94, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Mon Eki', 67387, 69, 167852, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 443, 95, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Algharbiat rid', 44398, 71, 142091, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 431, 96, 14, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Nurkrom', 107221, 124, 88424, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 305, 97, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Tower of guld-mazar', 46800, 70, -10850, 1, 0, '8 small Houses', 'none', 'Smith, Commander', 'STRONGHOLD', 70,
        98, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('ignis fire of the forest', 43718, 71, 39810, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 199, 99,
        26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Althanc', 52900, 90, -4900, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 71, 100, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Rauros Bane', 68710, 88, 51304, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 205, 101, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Khasbab al arz', 27924, 82, 145170, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 430, 102, 14, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Harnacla', 35837, 72, 11330, 0, 0, '8 small Houses', 'none', 'Bardenter', 'VILLAGE', 132, 103, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos Jaldar', 16850, 70, -18525, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 114, 104, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('qimat aljabal', 19070, 105, 150076, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 447, 105, 14, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Denerond', 31373, 64, 4672, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 130, 106, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Thuin Boid', 36162, 71, 5922, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 131, 107, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Village of Nootus Noot', 44161, 120, 17855, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 135, 108,
        5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Hiriath Village', 21146, 71, 18039, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 192, 109, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Village of Ene', 33937, 79, 20680, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 176, 110, 5, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Workshop of Doom', 45110, 57, 37655, 0, 0, '12 small Houses', 'Bombs, Catapult, Ram', 'Bartender', 'VILLAGE',
        172, 111, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Maldar', 12150, 64, -4600, 0, 0, '8 small Houses', 'None', 'Bartender', 'VILLAGE', 126, 112, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Vrulgol', 56185, 90, -5790, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 72, 113, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gwaelin', 19981, 71, 13789, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 180, 114, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Edras', 41491, 70, 12959, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 133, 115, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Glad Ereg', 44934, 83, 1484, 0, 0, '0', 'None', 'Smith,Commander', 'KEEP', 132, 116, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Azulmor', 84274, 72, 103598, 1, 0, 'none', 'none', 'Commander, Smith', 'CASTLE', 343, 117, 20, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Minas Malloth', 30391, 64, 2769, 1, 0, '4 small', 'none', 'Commander, smith', 'STRONGHOLD', 130, 118, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Marishburh', 23284, 70, 18842, 0, 0, '8 small', 'none', 'Bartender', 'VILLAGE', 177, 119, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Black- Mouth Castle', 75090, 90, 80040, 1, 0, 'none', 'none', 'Commander, Smith', 'CASTLE', 309, 120, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Ló Near', 18340, 64, 642, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 129, 121, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dol Orme', 36560, 72, 11394, 1, 0, 'none', 'none', 'Smith', 'CASTLE', 132, 122, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fel Cosit', 41229, 82, 20995, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 175, 123, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Laldir', 19995, 71, 13939, 1, 0, 'none', 'none', 'Smith-Commander', 'CASTLE', 180, 124, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Metriath', 19839, 71, 13907, 1, 0, 'none', 'none', 'Smith-Commander', 'CASTLE', 180, 125, 30, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Apple town', -17679, 77, 1241, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 121, 126, 10, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fornurnen Peace', 113281, 68, 76310, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 302, 127, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Trumarg Camp', 81292, 100, 62097, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 264, 128, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Agrafzahar', -23152, 77, -25050, 1, 1, '17 small Houses-4 large Houses', 'none', 'smith-commander-Bartender',
        'TOWN', 63, 129, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Gûl-lug', 67105, 63, 48499, 1, 0, 'none', 'none', 'Smith-Commander', 'CASTLE', 203, 130, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Veysun', 78150, 70, 71559, 1, 1, '28 small Houses, 4 large Houses', 'none',
        'Bartender, Oddment-collector, Commander', 'TOWN', 336, 131, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Minas Pilinoth', 71699, 70, 62340, 0, 0, '32 small houses, 4 Large houses', 'None',
        'Bartender, Smith, Commander', 'TOWN', 261, 132, 21, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Destroyed Hope Castle', 79267, 96, 68530, 1, 0, 'none', 'none', 'Smith-Commander', 'CASTLE', 310, 133, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dusty Ford', 121641, 68, 60310, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 298, 134, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Village of Westford', 42100, 80, 48297, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 200, 135, 5,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Markhu Faham', -14324, 70, -35589, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 44, 136, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Crying Spirit Mine', 104312, 82, 72939, 0, 0, '12 small Houses', 'none', 'Bartender', 'VILLAGE', 301, 137, 27,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Amon Eotheod', 63425, 88, 49050, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 201, 138, 24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Atalak-Grom', 86972, 148, 86803, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 306, 139, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Anduins Bane', 69711, 68, 52106, 1, 0, '8 small Houses', 'none', 'Smith-Commander', 'STRONGHOLD', 241, 140, 27,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Spiders Forest Village', 89439, 68, 83968, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 307, 141,
        27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dagorlads Ruin', 84426, 66, 48093, 1, 0, 'None', 'None', 'Smith, Commander', 'CASTLE', 207, 142, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('roavaverdam', 60876, 85, 36813, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 202, 143, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dul-Zargad', 23600, 76, -1955, 1, 0, '8 (12) small Houses', 'Ladder, Catapult, Siege Tower', 'Smith-Commander',
        'STRONGHOLD', 178, 144, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Dhul Voldir', 117067, 77, -14923, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 101, 145, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Screaming Bog Castle', 69867, 68, 52561, 1, 0, 'none', 'none', 'Smithy, Commander', 'CASTLE', 242, 146, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Between the two', 102327, 76, 67756, 0, 0, '8 small Houses', 'none', 'Bartender', 'VILLAGE', 300, 147, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Assagay', 125914, 68, 176537, 0, 0, '9 small Houses', 'none', 'Bartender, Hutmaker', 'VILLAGE', 441, 148, 12,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Armenos', -18738, 70, -1844, 1, 0, 'none', 'none', 'Smith-Lord', 'CASTLE', 119, 149, 10, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lingwirin', -37947, 770, 2106, 0, 0, '8 small Houses', 'none', 'Vintner Elf', 'VILLAGE', 57, 150, 10, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Goth-Fushaum', 107883, 68, 80803, 0, 0, '16 small Houses, 4 large Houses', 'none',
        'Bartender, Commander, Oddment', 'TOWN', 303, 151, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Lostladen', 102319, 72, 90011, 0, 0, '8 Small Houses', 'None', 'Bartender', 'VILLAGE', 345, 152, 27, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fort Golden Prime', 131047, 63, 27574, 1, 0, 'None', 'None', 'Commander', 'CASTLE', 214, 153, 28, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Eshowe war kraal', 95205, 73, 185851, 1, 0, 'none', 'none', 'Commander, Smith', 'CASTLE', 459, 154, 12, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fortress of the First Marshal', 53288, 69, 55558, 1, 0, '0', 'none', 'Commander, Smith', 'CASTLE', 245, 155,
        24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Riders Rest', 54867, 72, 39357, 1, 0, '0', 'None', 'Commander, Smith', 'CASTLE', 201, 156, 24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Fortress of Flowers', 96938, 69, -6996, 1, 0, '0', 'none', 'Smith, Commander', 'CASTLE', 150, 157, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Emyn Ortheiad', 77715, 71, -13054, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 146, 158, 16, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Anduins Horse', 67998, 72, 39471, 0, 0, '8', 'none', 'Bartender', 'VILLAGE', 201, 159, 24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Bonoldur', -18887, 72, -33962, 0, 0, '8', 'None', 'Barkeeper', 'VILLAGE', 50, 160, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Keldaruhm', -19058, 91, -31459, 0, 0, 'none', 'None', 'Smith, Commander', 'KEEP', 52, 161, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Thulgolir', -22028, 87, -7948, 2, 1, '2 mansions, 8 large houses, 192 small houses', 'none',
        'Bartender, Commander, Oddment collector, Wandering trader', 'CAPITAL', 61, 162, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Bimoldur', -18047, 76, -34996, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 50, 163, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Kegrigh', -15862, 127, -31558, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 63, 164, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Bhurngalor', -21414, 126, -16619, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 62, 165, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Nildirth', -28608, 77, -24875, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 53, 166, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Nalgrin', -23463, 93, -33117, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 51, 167, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Haggalir', -25774, 87, -10133, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 60, 168, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Delyur', -26086, 72, -33101, 0, 0, '16 small houses, 4 large houses', 'none',
        'Bartender, Commander, Oddment-collector', 'TOWN', 51, 169, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Vol Dural', -2206, 75, -35986, 0, 0, 'none', 'none', 'Commander, Smith', 'KEEP', 44, 170, 23, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Hurph', 81118, 64, -22143, 0, 0, '12 small houses', 'none', 'Bartender', 'VILLAGE', 38, 171, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Underharrow', 46628, 76, 53024, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 246, 172, 24, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Desolations Frontier', 102210, 200, -11654, 1, 0, 'none', 'none', 'Smith, Commander', 'CASTLE', 151, 173, 19,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Bree', 13676, 68, 583, 2, 1, '2 mansions, 8 large houses, 70 small houses', 'none',
        'Blacksmith, Brewer, Baker, Bartender, Commander, oddment collector, wandering trader', 'CAPITAL', 128, 174, 29,
        0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Briedget', 80973, 181, -223622, 1, 1, '4 large houses, 66 small houses', 'none',
        'Bartender, Commander, Oddment Collector', 'TOWN', 40, 175, 19, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Andos-Razhag', 50330, 109, -670, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 134, 176, 25, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('den of Vipers', 47632, 75, 17455, 0, 0, '8 small houses', 'none', 'Bartender', 'VILLAGE', 171, 177, 26, 0);

INSERT INTO claimbuilds (name, x, y, z, free_armies_remaining, free_trading_companies_remaining, number_of_houses,
                         siege, traders, type, region, id, owned_by, version)
VALUES ('Migdarth', -23652, 83, -17070, 0, 0, 'none', 'none', 'Smith, Commander', 'KEEP', 61, 178, 23, 0);


-- Add production sites in claimbuilds
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 22);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (13, 1, 22);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (126, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (86, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (46, 2, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 5, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (62, 2, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (61, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (121, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (120, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (98, 1, 23);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (101, 1, 30);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (48, 1, 30);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (73, 3, 33);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (103, 1, 33);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 1, 34);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 34);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 34);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 34);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (80, 1, 36);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (1, 1, 36);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (17, 1, 36);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 6, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 2, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (104, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (98, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (80, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (45, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (66, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (79, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (62, 5, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (61, 5, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 2, 37);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 39);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 39);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 40);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 42);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 42);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (13, 1, 43);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 43);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (117, 1, 45);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 45);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 46);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 47);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 48);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (117, 1, 48);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (5, 1, 49);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (189, 1, 49);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 2, 50);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (59, 1, 50);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (73, 1, 51);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 51);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (34, 1, 52);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 52);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (17, 1, 53);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 53);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 53);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (188, 1, 53);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 2, 53);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 54);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (81, 1, 54);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (46, 2, 55);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 55);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (190, 1, 56);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 56);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 57);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 57);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (81, 1, 58);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 58);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 59);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 59);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 61);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 61);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 62);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 63);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (80, 1, 63);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (86, 1, 64);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 64);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (62, 1, 65);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (82, 1, 65);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 4, 66);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 66);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 1, 66);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 67);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (62, 1, 67);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 68);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (49, 1, 68);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 70);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (74, 1, 70);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 70);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 74);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (68, 1, 74);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (67, 1, 74);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 1, 74);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (126, 1, 75);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 75);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 78);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 78);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 79);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 79);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (41, 1, 80);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (119, 1, 80);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (51, 1, 81);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 81);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (119, 1, 82);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (41, 1, 82);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 83);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 83);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (82, 1, 84);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (137, 1, 84);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (13, 2, 84);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 85);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 85);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (125, 1, 86);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 86);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (74, 14, 87);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (75, 16, 87);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 5, 87);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 6, 87);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (13, 8, 87);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 90);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 90);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (119, 1, 91);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (118, 1, 91);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (51, 1, 91);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 92);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 92);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 94);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 94);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 1, 94);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (118, 1, 95);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (16, 1, 95);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (97, 1, 96);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (86, 1, 96);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 98);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 98);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 99);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 1, 99);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 100);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (63, 2, 100);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (86, 1, 102);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (41, 1, 102);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 103);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 1, 103);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 104);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 1, 104);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (33, 1, 105);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 105);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 106);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 1, 106);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 107);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 2, 107);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (71, 1, 108);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 108);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 109);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 109);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 110);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (49, 1, 110);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (137, 1, 111);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 111);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 111);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 112);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 112);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 113);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 1, 113);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 1, 113);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 114);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (49, 1, 114);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 115);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 115);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 118);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 119);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 119);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 121);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 121);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 123);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 123);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (23, 1, 126);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (80, 1, 126);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (189, 1, 126);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 127);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 127);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 1, 128);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 128);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (170, 1, 129);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (4, 1, 129);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 129);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (123, 1, 129);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (27, 1, 129);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (46, 1, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (15, 2, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (23, 1, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 131);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (46, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (41, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (36, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (57, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (39, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 132);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 134);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (74, 2, 134);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 135);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 135);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (27, 1, 136);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 136);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (74, 1, 137);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (82, 1, 137);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (13, 1, 137);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (35, 1, 138);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 138);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 140);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 141);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (51, 2, 141);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 143);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (19, 1, 143);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (137, 1, 144);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 144);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (98, 1, 145);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (9, 1, 145);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (82, 1, 147);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (72, 1, 147);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 148);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 2, 150);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (138, 3, 151);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 2, 151);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (191, 1, 152);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (115, 1, 152);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (174, 1, 158);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 158);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 159);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (11, 1, 159);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (84, 1, 160);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 1, 160);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (79, 2, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (66, 1, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 1, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (64, 1, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (86, 1, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (190, 18, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (61, 6, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 3, 162);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (66, 1, 169);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (116, 1, 169);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (138, 1, 169);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (52, 2, 171);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 171);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 172);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 172);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 2, 174);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (105, 1, 174);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 8, 175);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (189, 2, 175);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (61, 6, 175);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (114, 1, 175);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (78, 1, 176);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (129, 1, 176);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (90, 1, 177);
INSERT INTO production_claimbuild (production_site_id, count, claimbuild_id)
VALUES (58, 1, 177);

-- Add special buildings in claimbuilds
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 22);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('BANK', 23);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 23);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 23);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 23);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 30);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 33);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 34);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 35);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 35);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 36);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 37);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('BANK', 37);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 37);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 37);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 37);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 38);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 39);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 40);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 40);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 40);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 41);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 41);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 44);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 44);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 45);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 46);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 46);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 46);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('EMBASSY', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 50);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 51);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 52);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 56);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('EMBASSY', 56);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 56);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 57);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 58);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 59);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 60);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 60);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 60);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 61);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 62);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 62);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 62);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 63);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 64);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 65);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 66);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 66);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 66);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 67);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 68);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 69);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 69);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 70);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WATCHTOWER', 70);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 70);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 70);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 70);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 71);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 71);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 72);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 72);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 73);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 73);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 74);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 74);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 74);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 76);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 76);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 77);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 77);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 78);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 79);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 80);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 81);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 82);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 83);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 84);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 84);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 84);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WATCHTOWER', 84);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 85);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 86);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 87);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 87);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 87);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 87);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 87);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 88);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 88);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 89);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 89);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 90);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 91);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 92);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 93);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 93);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 93);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 94);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 95);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 96);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 97);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 97);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 98);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 98);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 98);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 99);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 100);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 101);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 101);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 102);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 103);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 104);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 105);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 106);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 107);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 108);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 109);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 110);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 111);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 112);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 113);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 114);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 115);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 116);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 117);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 117);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 118);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 118);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 118);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 120);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 120);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 121);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 122);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 122);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 123);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 124);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 124);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 125);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 125);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 126);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 127);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 128);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 129);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 129);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 130);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 130);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 131);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 131);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 131);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WATCHTOWER', 131);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 131);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 132);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 132);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 132);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 133);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 133);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 134);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 135);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 136);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 137);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 138);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 139);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 139);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 140);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 140);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 140);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 141);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 142);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 142);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 143);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 144);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 144);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 144);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 145);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 146);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 146);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 147);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 148);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 149);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 149);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 150);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 151);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 151);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 151);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 152);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 153);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 154);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 154);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 155);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 155);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 156);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 156);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 157);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 157);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 158);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 159);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 160);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 161);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('BANK', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 162);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 163);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 164);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 165);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 166);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 167);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 168);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 169);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 169);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 169);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HARBOUR', 169);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 169);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 170);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 171);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 172);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 173);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 173);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('HOUSE_OF_HEALING', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('SHOP', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 174);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 175);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('MARKET', 175);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 175);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('STABLES', 175);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 176);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('INN', 177);
INSERT INTO claimbuild_special_buildings (special_buildings, claimbuild_id)
VALUES ('WALL', 178);

-- Add roleplay characters
INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (1, 0, true, 'Morwaith Armour and Weapons', NULL, 'Cetshwayo koMpande', false, 'Lion of Morwaith', 461, 37);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (2, 0, true,
        'Gundabad Uruk Helmet with Angmar armour, Arnorian Spear, Angmar Warhammer, Orc Bow and Poisoned Angmar Dagger',
        NULL, 'Knargaz', true, 'Knargaz of Angmar', 68, 72);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (3, 0, true, 'Gondolin', NULL, 'Firyawë', true, 'Lost Prince In Gondor', 168, 2);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (4, 0, true, 'Rohirric Armor and weapons, Barrow blade', NULL, 'Helm Hammerhand', true, 'Wight King of Rohan',
        245, 4);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (5, 0, true, 'Half troll armour, iron armour, half troll arsenal, bow and a random brick', NULL, 'Lagmag', true,
        'Lagging Half Troll', 494, 23);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (6, 0, true, 'Morgul Blade/Armor, Rhudel Bow, Iron X-Bow', NULL, 'Khamûl', true, 'Shadow of the East', 215, 7);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (7, 0, true, 'Iron, chain or leather armor, Arnorian swords and spear adn iron gear', NULL, 'Henry Hazelshaw',
        true, 'Mayor of Bree', 128, 32);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (8, 0, true, 'Golden Trimmed Dwarven Armour, Dorwinion Elf Helmet, Dwarven arsenal', NULL, 'Midas', true,
        'Durins Folk Treasurer', 102, 11);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (9, 0, true, 'Ranger armor, ranger bow, ranger sword and iron pike', NULL, 'Eredhon', true, 'King of Arnor', 131,
        27);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (10, 0, true, 'Dwarven Armor And Weapons Iron Crossbow', NULL, 'Iðunn', true, 'Durins Jewel', 148, 28);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (11, 0, true, 'Morgul armour, Morgul blade, Orc bow, Mordor Warscythe', NULL, 'Donar', true,
        'The Witch-King of Angmar', 267, 39);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (12, 0, true, 'Gondorian Armor And Weapon', NULL, 'Lorel Daughter Of Naruwel', true, 'Gondorian Architect', 263,
        10);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (13, 0, true, 'Umbar Arsenal And Gear Harad Bow Bn Helmet And Mace', NULL, 'Salazir', true, 'Corsair', 395, 29);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (14, 0, true, 'Da Armor Da Sword Da Poison Dagger Lindon Spear Gondor Bow', NULL, 'Apples', true,
        'Blade Of The Swan', 332, 13);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (15, 0, true, 'Fur Armour Iron/stone Spear Iron Crossbow Normal Bow Ered Luin Gear', NULL, 'Irdlirvirisissong',
        true, 'Snowman Of Ered Luin', 52, 8);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (16, 0, true, 'Dwarven Gear Dwarven Arsenal Ironcrossbow', NULL, 'Vidarr The Grimm', false,
        'Dwarven Boar-Whisperer', 102, 30);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (17, 0, true, 'Dwarven Arsenal And Gear Iron Crossbow', NULL, 'Stig Fornburg', true, 'The Iron Heart Of Durin',
        103, 17);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (18, 0, true,
        'Trimmed Dwarven Armour Dwarven Weapons Blue Dwarven Battleaxe Bladorthin Spear Iron Crossbow Regular Bow',
        NULL, 'Feros Wargslayer', true, 'Durin´s Hammer', 148, 9);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (19, 0, true, 'Gondor Armor And Weapons', NULL, 'Jonathel', true, 'Gondor Architect', 261, 5);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (20, 0, true, 'Golden Easterling Armour + Rhudel Weapons', NULL, 'Pallando', true, 'Blue Wizard Of Rhúdel', 215,
        12);
INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (21, 0, true, 'Standard Umbaric Equipment Dol Amroth Helmet Haradric Bow', NULL, 'Kazimierz', true,
        'Corsair Of Umbar', 395, 35);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (22, 0, true, 'Dol Amroth And Blackroot Vale Gear With A Crossbow', NULL, 'Lauthber', true,
        'Sergant Of Dol Amroth', 332, 31);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (23, 0, true, 'Full Taurethrim Arsenal And Blowgun Golden Taurethrim Armor Morwaith Spear', NULL, 'Chûn-Kai',
        true, 'Jungle Guardian', 546, 40);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (24, 0, true, 'Umbar Arsenal And Gear Haradric Bow (default For Umbar)', NULL, 'Kaseem Meer', true,
        'Liberator Of Umbar', 395, 36);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (25, 0, true, 'Blue Mountain Gear', NULL, 'Erik Blodoeks', true, 'Blue Mountain Warrior', 52, 24);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (26, 0, true, 'Morgul Armor Morgul Blade', NULL, 'The Uncleansed', true, 'The Spawn Of Mordor', 267, 42);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (27, 0, true, 'Southron Armor Icron Crossbow And Sword(southron Coast)', NULL, 'Vitellius Donatello', false,
        'The Southron Architect', 426, 38);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (28, 0, true, 'Taurethrim Gear Regular Bow Some Iron Weapons', NULL, 'Yakta', true, 'Taurethrim Inventor', 546,
        19);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (29, 0, true, 'Haradric Sword Spear Ithilien Ranger Armor Ranger Bow', NULL, 'Bêlzagar', true,
        'The Nomadic Exile', 400, 16);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (30, 0, true, 'Gondor Armour + Weapons + Bows', NULL, 'Belegorn Arnorion', true, 'Leader Of The Gondorians', 263,
        1);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (31, 0, true,
        'Bn Spear Bronze X-bow Rhunic Weapons/bow (no Spear) Rhunic Helm Bn Chest/pants Umbaric Boots Reed Armour',
        NULL, 'Iaudun', true, 'Desert Nomad', 400, 3);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (32, 0, true, 'Black Uruk Armour And Weapons', NULL, 'Athre Mugle', true, 'Black Uruk', 267, 14);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (33, 0, true, 'Gundabad Uruk Armor And Weapons Orc Bow And Boar Mount', NULL, 'Bonkboar', false,
        'Butcher Of Gundabad', 41, 15);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (34, 0, true, 'Ranger armor, ranger bow, arnorian sword, iron pike', NULL, 'Etheldin', true,
        'Dúnedain of the Angle', 131, 62);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (35, 0, true, 'Gondorian Amor, Sword and Bow', NULL, 'Erenthir', false, 'Swordsman of Gondor', 263, 69);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (36, 0, true, 'Angmar armour and weapons', NULL, 'Fun-Din-Dun', true, 'Slave of Angmar', 68, 64);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (37, 0, true, 'Isengard armor, berserker Helmet, Isengard weapons and tools', NULL, 'Ugzoc', true,
        'The Isengard ent slayer', 197, 71);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (38, 0, true, 'Pinath Gelin Armor Gondorian Sword And Blackroot Bow', NULL, 'Mablung Enthelor', true,
        'Pinnath Gelin Soldier', 263, 45);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (39, 0, true, '1 Pinnath Gelin Armour 2 Lossarnach Battleaxe 3 Gondorian Pike 4 Gondorian Bow 5 Gondorian Sword',
        NULL, 'Ithilian Enthelor', true, 'Pinnath Gelin Soldier', 263, 46);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (40, 0, true, 'Gold-trimmed Dwarf Armor Dwarven Battle Axe Dwarven Shield', NULL, 'Dwalin', true,
        'Dwarf Of Erebor', 102, 41);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (41, 0, true, 'Rivendell Sword & Armour Crossbow', NULL, 'Willi Celelas', true, 'Healing Hobbit Toe', 184, 52);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (42, 0, true,
        'Morgul Armor Morgul Blade Mordor Melee Weapons Mordor Orc Bow 1 Iron Crossbow Angmar Melee Weapons', NULL,
        'Witch-King', true, 'Mordor Sorcerer', 267, 50);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (43, 0, true, 'Southron Armor Champion Helmet Southron Weapon Taurethrim Blowgun', NULL, 'Khalil', true,
        'Patron Of Southron', 426, 53);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (44, 0, true, 'Dwarven Armor And Weapons Iron Crossbow Vanilla Bow', NULL, 'Frar', true,
        'Longbeard Errant-Warrior', 102, 58);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (45, 0, true, 'Umbar armor, Umbar Battleaxe', NULL, 'Allakthor', true, 'Havenmaster of Umbar', 395, 43);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (46, 0, true, 'Ranger Armor, Ranger Bow, Arnorian Sword, Iron Pike', NULL, 'Ernarion', true,
        'Dúnedain of the Angle', 131, 70);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (47, 0, true, 'Blue Mountain Armor, War-axe, Pike, Throwing-axe and iron Crossbow', NULL, 'Dwalin Ironfoot',
        true, 'Dwarf of Ered Luin', 52, 73);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (48, 0, true, 'Angamr Armour and Weapons', NULL, 'Boing', true, 'Shaman of Angmar', 68, 74);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (49, 0, true, 'Black Uruk cleaver, Black uruk Armor, pants and boots, black leather hat with a green feather',
        NULL, 'Lev Skywood', false, 'Black Morgul Shipwright', 267, 61);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (50, 0, true, 'Gondorian Armor, Lossarnach Chestplate, Lossarnach Battleaxe and Gondorian Weapons', NULL,
        'Forlong', true, 'Protector of Lossarnach', 263, 6);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (51, 0, true, 'Gundabar Uruk armor, cleaver, bow, pike, spear', NULL, 'Gong son of Bolg', true,
        'Mighty uruk of Angmar', 41, 63);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (52, 0, true, 'Uruk Armour, berserker Helmet, Uruk cleaver and spear, Orc Bow and skull staff', NULL, 'Lurtz',
        true, 'Leader of Isengard', 197, 65);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (53, 0, true,
        'Dwarven Armour, Dwarven Weapons/Equipment, Fur Armour, Iron Crossbow, Spear of Bladorthin, Galadhrim Bow',
        NULL, 'Denter Nienesson', true, 'Durins Banner', 102, 59);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (54, 0, true, 'Black Uruk Armor, Gundabad Helmet, Black Uruk Spear, War hammer und Bow, Morgul Blade', NULL,
        'Sauron', true, 'Dark Lord of Mordor', 267, 34);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (55, 0, true, 'Gundabad and Angmar Armor and Weapons', NULL, 'Dharg', true, 'Scar Artist of Angmar', 68, 75);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (56, 0, true, 'Dwarven Armour with Leather Hat (purple Feather), Dwarven Warhammer, Dagger', NULL, 'Mug', true,
        'The Durins Folk Hammer', 102, 80);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (57, 0, true, 'Lindon, Armor, Sword, Bow, Spear, Pike; Blacksmiths Hammer, Iron Crossbow', NULL, 'Menelvagor',
        true, 'Knight of Amon Ereb', 121, 76);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (58, 0, true, 'Uruk Armour with berserk helmet, Uruk warhammer, crossbow and pike', NULL, 'Arenod', true,
        'Shadow of Isengard', 197, 83);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (59, 0, true, 'Golden Rhunic set and a Kaftan, Full Rhunic Arsenal (hidden blade in rp only)', NULL, 'Dîla',
        true, 'Immortal of Rhûn', 215, 48);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (60, 0, true, 'Woodland realm armor with sword, bow and battlestaff', NULL, 'Northon', true,
        'of the Mirkwood Elves', 146, 44);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (61, 0, true, 'Wood elven Armor, sword and bow', NULL, 'Tatharion', true, 'The Mirkwood Warrior', 146, 54);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (62, 0, true, 'Blue Dwarven Armour Battleaxe And A Normal Bow', NULL, 'Thomir', true, 'King of Ered Luin', 61,
        51);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (63, 0, true, 'Rohirric Marshal Armour, Rohirris Battleaxe, Lance, Bow', NULL, 'Aldan', true, 'Light of Rohan',
        245, 84);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (64, 0, true, 'Winged Gondorian Helmet rest normal, Gondor Lance, Sword, Warhammer', NULL, 'Harlambos', true,
        'Gondor Knight', 326, 82);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (65, 0, true, 'Dol Amroth weapons and armor + Rivendell Bow and armor', NULL, 'Imrahil', true,
        'Retired Swan Prince', 332, 85);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (66, 0, true, 'Rohan Marshal armor and Rohan Arsenal', NULL, 'Ordric', true, 'Esquire of Rohan', 245, 79);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (67, 0, true, 'Silver trimmed dwarven armor, Dwarven Arsenal', NULL, 'Hjordlik the Mason', true, 'Durins Folk',
        102, 87);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (68, 0, true, 'Dwarven gear and arsenal', NULL, 'Alfrikr Chaosminer', false, 'Loremaster of Durins Folk', 102,
        88);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (69, 0, true, 'Rhun golden armor with a warlord helmet, Rhunic bow and Rhunic axe along with a Rhunic halberd',
        NULL, 'Hearun Archanis', true, 'King of Rhun', 215, 89);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (70, 0, true, 'Custom Armor and Arsenal', NULL, 'Aulendur', true, 'Elvensmith of Gondor', 323, 18);

INSERT INTO rpchars (id, version, active, gear, link_to_lore, name, pvp, title, current_region, owner_id)
VALUES (71, 0, true, 'Taurethrim Arsenal, Golden Taure Armor', NULL, 'Sweetpea', true, 'Desert Wanderer', 192, 33);

