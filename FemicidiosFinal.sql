
use femicidios;
/* CAMPOS CODIFICADOS */

/*
 Tablas sobre identidadGenero
 */

DROP TABLE IF EXISTS `ta_codigopaises`;
CREATE TABLE `ta_codigopaises`
(
    `spanish`      varchar(60) NOT NULL,
    `english`      varchar(60) NOT NULL,
    `ISO2`         varchar(3) DEFAULT NULL,
    `ISO3`         varchar(5) DEFAULT NULL,
    `telefonoPais` varchar(5) DEFAULT NULL,
    `Id`           int         NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`Id`)
);

INSERT INTO `ta_codigopaises`
VALUES ('Afganistán', 'Afghanistan', 'AF', 'AFG', '93', 1),
       ('Albania', 'Albania', 'AL', 'ALB', '355', 2),
       ('Alemania', 'Germany', 'DE', 'DEU', '49', 3),
       ('Algeria', 'Algeria', 'DZ', 'DZA', '213', 4),
       ('Andorra', 'Andorra', 'AD', 'AND', '376', 5),
       ('Angola', 'Angola', 'AO', 'AGO', '244', 6),
       ('Anguila', 'Anguilla', 'AI', 'AIA', '1 264', 7),
       ('Antártida', 'Antarctica', 'AQ', 'ATA', '672', 8),
       ('Antigua y Barbuda', 'Antigua and Barbuda', 'AG', 'ATG', '1 268', 9),
       ('Antillas Neerlandesas', 'Netherlands Antilles', 'AN', 'ANT', '599', 10),
       ('Arabia Saudita', 'Saudi Arabia', 'SA', 'SAU', '966', 11),
       ('Argentina', 'Argentina', 'AR', 'ARG', '54', 12),
       ('Armenia', 'Armenia', 'AM', 'ARM', '374', 13),
       ('Aruba', 'Aruba', 'AW', 'ABW', '297', 14),
       ('Australia', 'Australia', 'AU', 'AUS', '61', 15),
       ('Austria', 'Austria', 'AT', 'AUT', '43', 16),
       ('Azerbayán', 'Azerbaijan', 'AZ', 'AZE', '994', 17),
       ('Bahamas', 'Bahamas', 'BS', 'BHS', '1 242', 18),
       ('Bahrein', 'Bahrain', 'BH', 'BHR', '973', 19),
       ('Bangladesh', 'Bangladesh', 'BD', 'BGD', '880', 20),
       ('Barbados', 'Barbados', 'BB', 'BRB', '1 246', 21),
       ('Bélgica', 'Belgium', 'BE', 'BEL', '32', 22),
       ('Belice', 'Belize', 'BZ', 'BLZ', '501', 23),
       ('Benín', 'Benin', 'BJ', 'BEN', '229', 24),
       ('Bhután', 'Bhutan', 'BT', 'BTN', '975', 25),
       ('Bielorrusia', 'Belarus', 'BY', 'BLR', '375', 26),
       ('Birmania', 'Myanmar', 'MM', 'MMR', '95', 27),
       ('Bolivia', 'Bolivia', 'BO', 'BOL', '591', 28),
       ('Bosnia y Herzegovina', 'Bosnia and Herzegovina', 'BA', 'BIH', '387', 29),
       ('Botsuana', 'Botswana', 'BW', 'BWA', '267', 30),
       ('Brasil', 'Brazil', 'BR', 'BRA', '55', 31),
       ('Brunéi', 'Brunei', 'BN', 'BRN', '673', 32),
       ('Bulgaria', 'Bulgaria', 'BG', 'BGR', '359', 33),
       ('Burkina Faso', 'Burkina Faso', 'BF', 'BFA', '226', 34),
       ('Burundi', 'Burundi', 'BI', 'BDI', '257', 35),
       ('Cabo Verde', 'Cape Verde', 'CV', 'CPV', '238', 36),
       ('Camboya', 'Cambodia', 'KH', 'KHM', '855', 37),
       ('Camerún', 'Cameroon', 'CM', 'CMR', '237', 38),
       ('Canadá', 'Canada', 'CA', 'CAN', '1', 39),
       ('Chad', 'Chad', 'TD', 'TCD', '235', 40),
       ('Chile', 'Chile', 'CL', 'CHL', '56', 41),
       ('China', 'China', 'CN', 'CHN', '86', 42),
       ('Chipre', 'Cyprus', 'CY', 'CYP', '357', 43),
       ('Ciudad del Vaticano', 'Vatican City State', 'VA', 'VAT', '39', 44),
       ('Colombia', 'Colombia', 'CO', 'COL', '57', 45),
       ('Comoras', 'Comoros', 'KM', 'COM', '269', 46),
       ('Congo', 'Congo', 'CG', 'COG', '242', 47),
       ('Congo', 'Congo', 'CD', 'COD', '243', 48),
       ('Corea del Norte', 'North Korea', 'KP', 'PRK', '850', 49),
       ('Corea del Sur', 'South Korea', 'KR', 'KOR', '82', 50),
       ('Costa de Marfil', 'Ivory Coast', 'CI', 'CIV', '225', 51),
       ('Costa Rica', 'Costa Rica', 'CR', 'CRI', '506', 52),
       ('Croacia', 'Croatia', 'HR', 'HRV', '385', 53),
       ('Cuba', 'Cuba', 'CU', 'CUB', '53', 54),
       ('Dinamarca', 'Denmark', 'DK', 'DNK', '45', 55),
       ('Dominica', 'Dominica', 'DM', 'DMA', '1 767', 56),
       ('Ecuador', 'Ecuador', 'EC', 'ECU', '593', 57),
       ('Egipto', 'Egypt', 'EG', 'EGY', '20', 58),
       ('El Salvador', 'El Salvador', 'SV', 'SLV', '503', 59),
       ('Emiratos Árabes Unidos', 'United Arab Emirates', 'AE', 'ARE', '971', 60),
       ('Eritrea', 'Eritrea', 'ER', 'ERI', '291', 61),
       ('Escocia', 'Scotland', 'GB', '', '44', 62),
       ('Eslovaquia', 'Slovakia', 'SK', 'SVK', '421', 63),
       ('Eslovenia', 'Slovenia', 'SI', 'SVN', '386', 64),
       ('España', 'Spain', 'ES', 'ESP', '34', 65),
       ('Estados Unidos de América', 'United States of America', 'US', 'USA', '1', 66),
       ('Estonia', 'Estonia', 'EE', 'EST', '372', 67),
       ('Etiopía', 'Ethiopia', 'ET', 'ETH', '251', 68),
       ('Filipinas', 'Philippines', 'PH', 'PHL', '63', 69),
       ('Finlandia', 'Finland', 'FI', 'FIN', '358', 70),
       ('Fiyi', 'Fiji', 'FJ', 'FJI', '679', 71),
       ('Francia', 'France', 'FR', 'FRA', '33', 72),
       ('Gabón', 'Gabon', 'GA', 'GAB', '241', 73),
       ('Gales', 'Wales', 'GB', '', '44', 74),
       ('Gambia', 'Gambia', 'GM', 'GMB', '220', 75),
       ('Georgia', 'Georgia', 'GE', 'GEO', '995', 76),
       ('Ghana', 'Ghana', 'GH', 'GHA', '233', 77),
       ('Gibraltar', 'Gibraltar', 'GI', 'GIB', '350', 78),
       ('Granada', 'Grenada', 'GD', 'GRD', '1 473', 79),
       ('Grecia', 'Greece', 'GR', 'GRC', '30', 80),
       ('Groenlandia', 'Greenland', 'GL', 'GRL', '299', 81),
       ('Guadalupe', 'Guadeloupe', 'GP', 'GLP', '', 82),
       ('Guam', 'Guam', 'GU', 'GUM', '1 671', 83),
       ('Guatemala', 'Guatemala', 'GT', 'GTM', '502', 84),
       ('Guayana Francesa', 'French Guiana', 'GF', 'GUF', '', 85),
       ('Guernsey', 'Guernsey', 'GG', 'GGY', '', 86),
       ('Guinea', 'Guinea', 'GN', 'GIN', '224', 87),
       ('Guinea Ecuatorial', 'Equatorial Guinea', 'GQ', 'GNQ', '240', 88),
       ('Guinea-Bissau', 'Guinea-Bissau', 'GW', 'GNB', '245', 89),
       ('Guyana', 'Guyana', 'GY', 'GUY', '592', 90),
       ('Haití', 'Haiti', 'HT', 'HTI', '509', 91),
       ('Honduras', 'Honduras', 'HN', 'HND', '504', 92),
       ('Hong kong', 'Hong Kong', 'HK', 'HKG', '852', 93),
       ('Hungría', 'Hungary', 'HU', 'HUN', '36', 94),
       ('India', 'India', 'IN', 'IND', '91', 95),
       ('Indonesia', 'Indonesia', 'ID', 'IDN', '62', 96),
       ('Inglaterra', 'England', 'GB', '', '44', 97),
       ('Irak', 'Iraq', 'IQ', 'IRQ', '964', 98),
       ('Irán', 'Iran', 'IR', 'IRN', '98', 99),
       ('Irlanda', 'Ireland', 'IE', 'IRL', '353', 100),
       ('Irlanda del Norte', 'Northern Irland', 'GB', '', '44', 101),
       ('Isla Bouvet', 'Bouvet Island', 'BV', 'BVT', '', 102),
       ('Isla de Man', 'Isle of Man', 'IM', 'IMN', '44', 103),
       ('Isla de Navidad', 'Christmas Island', 'CX', 'CXR', '61', 104),
       ('Isla Norfolk', 'Norfolk Island', 'NF', 'NFK', '', 105),
       ('Islandia', 'Iceland', 'IS', 'ISL', '354', 106),
       ('Islas Bermudas', 'Bermuda Islands', 'BM', 'BMU', '1 441', 107),
       ('Islas Caimán', 'Cayman Islands', 'KY', 'CYM', '1 345', 108),
       ('Islas Cocos (Keeling)', 'Cocos (Keeling) Islands', 'CC', 'CCK', '61', 109),
       ('Islas Cook', 'Cook Islands', 'CK', 'COK', '682', 110),
       ('Islas de Åland', 'Åland Islands', 'AX', 'ALA', '', 111),
       ('Islas Feroe', 'Faroe Islands', 'FO', 'FRO', '298', 112),
       ('Islas Georgias del Sur y Sandwich del Sur', 'South Georgia and the South Sandwich Islands', 'GS', 'SGS', '',
        113),
       ('Islas Heard y McDonald', 'Heard Island and McDonald Islands', 'HM', 'HMD', '', 114),
       ('Islas Maldivas', 'Maldives', 'MV', 'MDV', '960', 115),
       ('Islas Malvinas', 'Falkland Islands (Malvinas)', 'FK', 'FLK', '500', 116),
       ('Islas Marianas del Norte', 'Northern Mariana Islands', 'MP', 'MNP', '1 670', 117),
       ('Islas Marshall', 'Marshall Islands', 'MH', 'MHL', '692', 118),
       ('Islas Pitcairn', 'Pitcairn Islands', 'PN', 'PCN', '870', 119),
       ('Islas Salomón', 'Solomon Islands', 'SB', 'SLB', '677', 120),
       ('Islas Turcas y Caicos', 'Turks and Caicos Islands', 'TC', 'TCA', '1 649', 121),
       ('Islas Ultramarinas Menores de Estados Unidos', 'United States Minor Outlying Islands', 'UM', 'UMI', '', 122),
       ('Islas Vírgenes Británicas', 'Virgin Islands', 'VG', 'VG', '1 284', 123),
       ('Islas Vírgenes de los Estados Unidos', 'United States Virgin Islands', 'VI', 'VIR', '1 340', 124),
       ('Israel', 'Israel', 'IL', 'ISR', '972', 125),
       ('Italia', 'Italy', 'IT', 'ITA', '39', 126),
       ('Jamaica', 'Jamaica', 'JM', 'JAM', '1 876', 127),
       ('Japón', 'Japan', 'JP', 'JPN', '81', 128),
       ('Jersey', 'Jersey', 'JE', 'JEY', '', 129),
       ('Jordania', 'Jordan', 'JO', 'JOR', '962', 130),
       ('Kazajistán', 'Kazakhstan', 'KZ', 'KAZ', '7', 131),
       ('Kenia', 'Kenya', 'KE', 'KEN', '254', 132),
       ('Kirgizstán', 'Kyrgyzstan', 'KG', 'KGZ', '996', 133),
       ('Kiribati', 'Kiribati', 'KI', 'KIR', '686', 134),
       ('Kuwait', 'Kuwait', 'KW', 'KWT', '965', 135),
       ('Laos', 'Laos', 'LA', 'LAO', '856', 136),
       ('Lesoto', 'Lesotho', 'LS', 'LSO', '266', 137),
       ('Letonia', 'Latvia', 'LV', 'LVA', '371', 138),
       ('Líbano', 'Lebanon', 'LB', 'LBN', '961', 139),
       ('Liberia', 'Liberia', 'LR', 'LBR', '231', 140),
       ('Libia', 'Libya', 'LY', 'LBY', '218', 141),
       ('Liechtenstein', 'Liechtenstein', 'LI', 'LIE', '423', 142),
       ('Lituania', 'Lithuania', 'LT', 'LTU', '370', 143),
       ('Luxemburgo', 'Luxembourg', 'LU', 'LUX', '352', 144),
       ('Macao', 'Macao', 'MO', 'MAC', '853', 145),
       ('Macedônia', 'Macedonia', 'MK', 'MKD', '389', 146),
       ('Madagascar', 'Madagascar', 'MG', 'MDG', '261', 147),
       ('Malasia', 'Malaysia', 'MY', 'MYS', '60', 148),
       ('Malawi', 'Malawi', 'MW', 'MWI', '265', 149),
       ('Mali', 'Mali', 'ML', 'MLI', '223', 150),
       ('Malta', 'Malta', 'MT', 'MLT', '356', 151),
       ('Marruecos', 'Morocco', 'MA', 'MAR', '212', 152),
       ('Martinica', 'Martinique', 'MQ', 'MTQ', '', 153),
       ('Mauricio', 'Mauritius', 'MU', 'MUS', '230', 154),
       ('Mauritania', 'Mauritania', 'MR', 'MRT', '222', 155),
       ('Mayotte', 'Mayotte', 'YT', 'MYT', '262', 156),
       ('México', 'Mexico', 'MX', 'MEX', '52', 157),
       ('Micronesia', 'Estados Federados de', 'FM', 'FSM', '691', 158),
       ('Moldavia', 'Moldova', 'MD', 'MDA', '373', 159),
       ('Mónaco', 'Monaco', 'MC', 'MCO', '377', 160),
       ('Mongolia', 'Mongolia', 'MN', 'MNG', '976', 161),
       ('Montenegro', 'Montenegro', 'ME', 'MNE', '382', 162),
       ('Montserrat', 'Montserrat', 'MS', 'MSR', '1 664', 163),
       ('Mozambique', 'Mozambique', 'MZ', 'MOZ', '258', 164),
       ('Namibia', 'Namibia', 'NA', 'NAM', '264', 165),
       ('Nauru', 'Nauru', 'NR', 'NRU', '674', 166),
       ('Nepal', 'Nepal', 'NP', 'NPL', '977', 167),
       ('Nicaragua', 'Nicaragua', 'NI', 'NIC', '505', 168),
       ('Niger', 'Niger', 'NE', 'NER', '227', 169),
       ('Nigeria', 'Nigeria', 'NG', 'NGA', '234', 170),
       ('Niue', 'Niue', 'NU', 'NIU', '683', 171),
       ('Noruega', 'Norway', 'NO', 'NOR', '47', 172),
       ('Nueva Caledonia', 'New Caledonia', 'NC', 'NCL', '687', 173),
       ('Nueva Zelanda', 'New Zealand', 'NZ', 'NZL', '64', 174),
       ('Omán', 'Oman', 'OM', 'OMN', '968', 175),
       ('Países Bajos', 'Netherlands', 'NL', 'NLD', '31', 176),
       ('Pakistán', 'Pakistan', 'PK', 'PAK', '92', 177),
       ('Palau', 'Palau', 'PW', 'PLW', '680', 178),
       ('Palestina', 'Palestine', 'PS', 'PSE', '', 179),
       ('Panamá', 'Panama', 'PA', 'PAN', '507', 180),
       ('Papúa Nueva Guinea', 'Papua New Guinea', 'PG', 'PNG', '675', 181),
       ('Paraguay', 'Paraguay', 'PY', 'PRY', '595', 182),
       ('Perú', 'Peru', 'PE', 'PER', '51', 183),
       ('Polinesia Francesa', 'French Polynesia', 'PF', 'PYF', '689', 184),
       ('Polonia', 'Poland', 'PL', 'POL', '48', 185),
       ('Portugal', 'Portugal', 'PT', 'PRT', '351', 186),
       ('Puerto Rico', 'Puerto Rico', 'PR', 'PRI', '1', 187),
       ('Qatar', 'Qatar', 'QA', 'QAT', '974', 188),
       ('Reino Unido', 'United Kingdom', 'GB', 'GBR', '44', 189),
       ('República Centroafricana', 'Central African Republic', 'CF', 'CAF', '236', 190),
       ('República Checa', 'Czech Republic', 'CZ', 'CZE', '420', 191),
       ('República Dominicana', 'Dominican Republic', 'DO', 'DOM', '1 809', 192),
       ('Reunión', 'Réunion', 'RE', 'REU', '', 193),
       ('Ruanda', 'Rwanda', 'RW', 'RWA', '250', 194),
       ('Rumanía', 'Romania', 'RO', 'ROU', '40', 195),
       ('Rusia', 'Russia', 'RU', 'RUS', '7', 196),
       ('Sahara Occidental', 'Western Sahara', 'EH', 'ESH', '', 197),
       ('Samoa', 'Samoa', 'WS', 'WSM', '685', 198),
       ('Samoa Americana', 'American Samoa', 'AS', 'ASM', '1 684', 199),
       ('San Bartolomé', 'Saint Barthélemy', 'BL', 'BLM', '590', 200),
       ('San Cristóbal y Nieves', 'Saint Kitts and Nevis', 'KN', 'KNA', '1 869', 201),
       ('San Marino', 'San Marino', 'SM', 'SMR', '378', 202),
       ('San Martín (Francia)', 'Saint Martin (French part)', 'MF', 'MAF', '1 599', 203),
       ('San Pedro y Miquelón', 'Saint Pierre and Miquelon', 'PM', 'SPM', '508', 204),
       ('San Vicente y las Granadinas', 'Saint Vincent and the Grenadines', 'VC', 'VCT', '1 784', 205),
       ('Santa Elena', 'Ascensión y Tristán de Acuña', 'SH', 'SHN', '290', 206),
       ('Santa Lucía', 'Saint Lucia', 'LC', 'LCA', '1 758', 207),
       ('Santo Tomé y Príncipe', 'Sao Tome and Principe', 'ST', 'STP', '239', 208),
       ('Senegal', 'Senegal', 'SN', 'SEN', '221', 209),
       ('Serbia', 'Serbia', 'RS', 'SRB', '381', 210),
       ('Seychelles', 'Seychelles', 'SC', 'SYC', '248', 211),
       ('Sierra Leona', 'Sierra Leone', 'SL', 'SLE', '232', 212),
       ('Singapur', 'Singapore', 'SG', 'SGP', '65', 213),
       ('Siria', 'Syria', 'SY', 'SYR', '963', 214),
       ('Somalia', 'Somalia', 'SO', 'SOM', '252', 215),
       ('Sri lanka', 'Sri Lanka', 'LK', 'LKA', '94', 216),
       ('Sudáfrica', 'South Africa', 'ZA', 'ZAF', '27', 217),
       ('Sudán', 'Sudan', 'SD', 'SDN', '249', 218),
       ('Suecia', 'Sweden', 'SE', 'SWE', '46', 219),
       ('Suiza', 'Switzerland', 'CH', 'CHE', '41', 220),
       ('Surinám', 'Suriname', 'SR', 'SUR', '597', 221),
       ('Svalbard y Jan Mayen', 'Svalbard and Jan Mayen', 'SJ', 'SJM', '', 222),
       ('Swazilandia', 'Swaziland', 'SZ', 'SWZ', '268', 223),
       ('Tadjikistán', 'Tajikistan', 'TJ', 'TJK', '992', 224),
       ('Tailandia', 'Thailand', 'TH', 'THA', '66', 225),
       ('Taiwán', 'Taiwan', 'TW', 'TWN', '886', 226),
       ('Tanzania', 'Tanzania', 'TZ', 'TZA', '255', 227),
       ('Territorio Británico del Océano Índico', 'British Indian Ocean Territory', 'IO', 'IOT', '', 228),
       ('Territorios Australes y Antárticas Franceses', 'French Southern Territories', 'TF', 'ATF', '', 229),
       ('Timor Oriental', 'East Timor', 'TL', 'TLS', '670', 230),
       ('Togo', 'Togo', 'TG', 'TGO', '228', 231),
       ('Tokelau', 'Tokelau', 'TK', 'TKL', '690', 232),
       ('Tonga', 'Tonga', 'TO', 'TON', '676', 233),
       ('Trinidad y Tobago', 'Trinidad and Tobago', 'TT', 'TTO', '1 868', 234),
       ('Tunez', 'Tunisia', 'TN', 'TUN', '216', 235),
       ('Turkmenistán', 'Turkmenistan', 'TM', 'TKM', '993', 236),
       ('Turquía', 'Turkey', 'TR', 'TUR', '90', 237),
       ('Tuvalu', 'Tuvalu', 'TV', 'TUV', '688', 238),
       ('Ucrania', 'Ukraine', 'UA', 'UKR', '380', 239),
       ('Uganda', 'Uganda', 'UG', 'UGA', '256', 240),
       ('Uruguay', 'Uruguay', 'UY', 'URY', '598', 241),
       ('Uzbekistán', 'Uzbekistan', 'UZ', 'UZB', '998', 242),
       ('Vanuatu', 'Vanuatu', 'VU', 'VUT', '678', 243),
       ('Venezuela', 'Venezuela', 'VE', 'VEN', '58', 244),
       ('Vietnam', 'Vietnam', 'VN', 'VNM', '84', 245),
       ('Wallis y Futuna', 'Wallis and Futuna', 'WF', 'WLF', '681', 246),
       ('Yemen', 'Yemen', 'YE', 'YEM', '967', 247),
       ('Yibuti', 'Djibouti', 'DJ', 'DJI', '253', 248),
       ('Zambia', 'Zambia', 'ZM', 'ZMB', '260', 249),
       ('Zimbabue', 'Zimbabwe', 'ZW', 'ZWE', '263', 250);

/*NUEVA TABLA DE IDENTIDAD DE GENERO*/
DROP TABLE IF EXISTS `ta_identidadesgeneros`;
CREATE TABLE `ta_identidadesgeneros`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `nombre`      varchar(55)  NOT NULL,
    `descripcion` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `ta_identidadesgeneros`
VALUES (1, 'Cis Hombre', 'Hombre identificado con el género que se le asignó al nacer.'),
       (2, 'Cis Mujer', 'Mujer identificado con el género que se le asignó al nacer.'),
       (3, 'No Binario', '	Persona no identificada con género masculino o femenino.'),
       (4, 'Otro', 'Otra identidad de género.'),
       (5, 'Se Desconoce', '	Se desconoce el género.'),
       (6, 'Mujer Trans', 'Mujer que no se identifica con el género que se le asignó al nacer.'),
       (7, 'Hombre Trans', 'Hombre que no se identifica con el género que se le asignó al nacer.');


/*
 Tabla SituacionJuridica
 */

DROP TABLE IF EXISTS ta_situacion_juridica;
CREATE TABLE femicidios.ta_situacion_juridica
(
    CI_Codigo      INT         NOT NULL AUTO_INCREMENT,
    CV_Titulo      VARCHAR(50) NOT NULL,
    CV_Descripcion VARCHAR(75) NOT NULL,
    PRIMARY KEY (CI_Codigo)
);

INSERT INTO ta_situacion_juridica (`CV_Titulo`, `CV_Descripcion`)
VALUES ('Imputado', ''),
       ('Indagado', ''),
       ('Procesado', ''),
       ('Acusado', ''),
       ('Condenado', ''),
       ('Fugitivo', ''),
       ('En libertad Condicional', ''),
       ('Prisión preventiva', ''),
       ('Otro', ''),
       ('Se Desconoce', '');

/*
 Tabla orientacionesSexuales
 */

DROP TABLE IF EXISTS `ta_orientacionessexuales`;
CREATE TABLE `ta_orientacionessexuales`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(50) NOT NULL,
    `CV_Descripcion` varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_orientacionessexuales`
VALUES (1, 'Lesbiana', 'Se reconoce lesbiana.'),
       (2, 'Gay', 'Se reconoce gay.'),
       (3, 'Heterosexual', 'Se reconoce heterosexual.'),
       (4, 'Bisexual', 'Se reconoce bisexual.'),
       (5, 'Asexual', 'Se reconoce Asexual.'),
       (6, 'Otro', 'Otra orientación sexual.'),
       (7, 'Se desconoce', 'Se desconoce la orientación sexual.');


/*
 Tabla Modalidades
 */

DROP TABLE IF EXISTS `ta_modalidades`;
CREATE TABLE `ta_modalidades`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(50) NOT NULL,
    `CV_Descripcion` varchar(50) NOT NULL,
    `CV_Pais` varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_modalidades`
VALUES (1, 'Golpes', 'Asesinada por golpes','Costa Rica'),
       (2, 'Disparo de bala', 'Asesinada por disparo de bala','Costa Rica'),
       (3, 'Apuñalamiento', 'Asesinada por apuñalamiento','Costa Rica'),
       (4, 'Quemaduras', 'Asesinada por quemaduras','Costa Rica'),
       (5, 'Asfixia', 'Asesinada por asfixia.','Costa Rica'),
       (6, 'Ahogamiento', 'Asesinada por ahogamiento','Costa Rica'),
       (7, 'Ataladramiento', 'Asesinada por ataladramiento','Costa Rica'),
       (8, 'Atropellamiento', 'Asesinada por atropellamiento','Costa Rica'),
       (9, 'Estrangulamiento', 'Asesinada por estrangulamiento','Costa Rica'),
       (10, 'Otros medios', 'Asesinada por otros medios','Costa Rica'),
       (11, 'Se Desconoce', 'Se desconoce la modalidad','Costa Rica');

/*
 Tabla NivelEducativo
 */
DROP TABLE IF EXISTS `ta_niveleducativo`;
CREATE TABLE `ta_niveleducativo`
(
    `CI_Id`          int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(50) NOT NULL,
    `CV_Descripcion` varchar(75) NOT NULL,
    `CV_Pais` varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Id`)
);

INSERT INTO `ta_niveleducativo`
VALUES (1, 'Primera infancia', 'Eduacion de la primera infancia.','Costa Rica' ),
       (2, 'Preescolar', 'Menos que primaria.','Costa Rica'),
       (3, 'Primaria', 'Educaciòn primaria', 'Costa Rica'),
       (4, 'Secundaria baja', 'Educaciòn secundaria baja','Costa Rica'),
       (5, 'Secundaria alta', 'Educaciòn secundaria alta','Costa Rica'),
       (6, 'Postsecundaria no terciaria', 'Educaciòn postsecundaria no terciaria','Costa Rica'),
       (7, 'Terciaria', 'Educaciòn terciaria de ciclo corto','Costa Rica'),
       (8, 'Grado', 'Grado en educaciòn universitaria o nivel equivalente.','Costa Rica'),
       (9, 'Maestria', 'Nivel de maestrìa especializaciòn o equivalente','Costa Rica'),
       (10, 'Doctorado', 'Nivel de doctorado o equivalente','Costa Rica'),
       (11, 'Se desconoce', 'Se desconoce nivel eduactivo','Costa Rica');


/*
 Tabla TipoLugar
 */

DROP TABLE IF EXISTS `ta_tipolugar`;
CREATE TABLE `ta_tipolugar`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(30) NOT NULL,
    `CV_Descripcion` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_tipolugar`
VALUES (1, 'Domicilio Víctima', 'Lugar de residencia particular de la víctima'),
       (2, 'Domicilio Víctimario', 'Lugar de residencia particular del víctimario'),
       (3, 'Domicilio Particular', 'Lugar de residencia no necesariamente de la víctima o victimario'),
       (4, 'Espacio Abierto', 'Calle o transporte público'),
       (5, 'Puesto de Trabajo', 'Lugar en el que se desempeñan sus tareas laborales habituales '),
       (6, 'Institución Educativa', 'Escuelas u otras instituciones educativas'),
       (7, 'Prisión', 'Instituciones penales o correccionales'),
       (8, 'Institución', 'Entornos de atención institucional (incluye: hospitales, intituciones)'),
       (9, 'Se desconoce', '');


/*
 Tabla TipoRelacion
 */
DROP TABLE IF EXISTS `ta_tiporelacion`;
CREATE TABLE `ta_tiporelacion`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(50) NOT NULL,
    `CV_Descripcion` varchar(80) NOT NULL,
    `CV_Pais` varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_tiporelacion`
VALUES (1, 'Padre', 'Victimario es padre de la víctima','Costa Rica'),
       (2, 'Madre', 'Victimario es madre de la víctima.','Costa Rica'),
       (3, 'Padrastro', 'Victimario es padrastro de la víctima.','Costa Rica'),
       (4, 'Madastra', 'Victimario es madrastra de la víctima.','Costa Rica'),
       (5, 'Tutor/a', 'Victimario es tutor/a de la víctima.','Costa Rica'),
       (6, 'Esposo/a', 'Victimario es esposo/a de la víctima.','Costa Rica'),
       (7, 'Concubino/a', 'Victimario es concubino/a de la víctima.','Costa Rica'),
       (8, 'Novio/a', 'Victimario es novio/a de la víctima.','Costa Rica'),
       (9, 'Amante', 'Victimario es amante de la víctima.','Costa Rica'),
       (10, 'Pareja Anterior', 'Pareja o cónyuge anterior.','Costa Rica'),
       (11, 'Pariente', 'Victimario es pariente consanguíneo de la víctima.','Costa Rica'),
       (12, 'Laboral', 'Victimario es empleado/a o colega de la víctima.','Costa Rica'),
       (13, 'Conocido/a', 'Victimario es conocido/a de la víctima.','Costa Rica'),
       (14, 'Amigo/a', 'Amigo/a de la víctima.','Costa Rica'),
       (15, 'Otro Transgresor', 'Otro transgresor conocido por la víctima.','Costa Rica'),
       (16, 'Autoridad', 'Por autoridades oficiales','Costa Rica'),
       (17, 'Desconocido/a', 'Victimario no es conocido/a por la víctima.','Costa Rica'),
       (18, 'Se desconoce', 'Se desconoce el tipo de relación.','Costa Rica'),
       (19, 'Ninguna', 'Victimario no tiene alguna relación con la víctima.','Costa Rica');
       



/*
 Tabla TipoVictima
 */

DROP TABLE IF EXISTS `ta_tipovictima`;
CREATE TABLE `ta_tipovictima`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(50) NOT NULL,
    `CV_Descripcion` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_tipovictima`
VALUES (1, 'Principal', 'La victima principal del femicidio'),
       (2, 'Vinculado', 'Victima vinculada al crimen principal');


/*
 Tabla TipoOrganismo
 */

DROP TABLE IF EXISTS `ta_tipoorganismo`;
CREATE TABLE `ta_tipoorganismo`
(
    `CI_Codigo`      int         NOT NULL AUTO_INCREMENT,
    `CV_Titulo`      varchar(80) NOT NULL,
    `CV_Descripcion` varchar(80) DEFAULT NULL,
	`CV_Paises` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`CI_Codigo`)
);

INSERT INTO `ta_tipoorganismo`
VALUES (1, 'Policial', 'Organismo policial incluyendo servicios de emergencia', 'Costa Rica'),
       (2, 'Judicial', 'Organismo Judicial a nivel nacional', 'Costa Rica'),
       (3, 'Fiscalia', 'Fiscalia', 'Costa Rica'),
       (4, 'Salud', 'Institucion de Salud', 'Costa Rica'),
       (5, 'Ministerio de Desarrollo Social', 'Ministerio de Desarrollo Social', 'Costa Rica'),
       (6, 'Justicia Provincial', 'Poder Judicial a nivel provincial', 'Costa Rica'),
       (7, 'Ministerio Publico Fiscal', ' ', 'Costa Rica'),
       (8, 'Ministerio de Justicia', 'Ministerio de Justicia', 'Costa Rica'),
       (9, 'Medicina', 'Medicina Legal o Forense', 'Costa Rica'),
       (10, 'Secretaria de la Mujer', 'Ministerio o Secretaria de la Mujer', 'Costa Rica'),
       (11, 'Administrativo', ' ', 'Costa Rica');



/* ENTIDADES */

/*
 Tabla de accesos y seguridad
 */

/*
 Tabla Perfil
 */

DROP TABLE IF EXISTS `th_bitacoras`;
DROP TABLE IF EXISTS `ta_usuarios`;
DROP TABLE IF EXISTS `ta_perfil`;
CREATE TABLE `ta_perfil`
(
    `CI_Id`          int         NOT NULL AUTO_INCREMENT,
    `CV_Descripcion` varchar(200) NOT NULL,
    `CV_rol`         varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Id`)
);
INSERT INTO `ta_perfil`
VALUES (1, 'Tiene acceso a todo', 'Administrador'),
       (2, 'No tiene acceso a las pantallas del admin', 'Convencional'),
       (3, 'No tiene acceso a las acciones', 'Consulta');


/*
    Tabla Usuarios
*/


DROP TABLE IF EXISTS `ta_usuarios`;
CREATE TABLE `ta_usuarios`
(
    `CI_Id`        int         NOT NULL AUTO_INCREMENT,
    `CV_Cedula`    varchar(20) DEFAULT NULL,
    `CV_Nombre`    varchar(50) NOT NULL,
    `CV_Apellidos` varchar(50) NOT NULL,
    `CI_Perfil`    int         NOT NULL,
    `Tc_Clave`     varchar(60) NOT NULL,
    PRIMARY KEY (`CI_Id`),
    KEY `CI_Perfil` (`CI_Perfil`),
    CONSTRAINT `ta_usuarios_ibfk_1` FOREIGN KEY (`CI_Perfil`) REFERENCES `ta_perfil` (`CI_Id`)
);

INSERT INTO `ta_usuarios`
VALUES (1, '702740601', 'David', 'vargas', 1, '$2a$10$5mZP.XO4VLMhqPaAldZ9ROB39W3.6p9uHZ.fDgoMVXVg0RhRmOyLS'),
       (2, '702740602', 'Ismael', 'Valverde', 2, '$2a$10$5mZP.XO4VLMhqPaAldZ9ROB39W3.6p9uHZ.fDgoMVXVg0RhRmOyLS'),
       (3, '702740603', 'Julio', 'Jarkin', 3, '$2a$10$5mZP.XO4VLMhqPaAldZ9ROB39W3.6p9uHZ.fDgoMVXVg0RhRmOyLS');
# 12345Jm#
/*
 Tabla Bitacoras
 */

DROP TABLE IF EXISTS `th_bitacoras`;
CREATE TABLE `th_bitacoras`
(
    `CI_Id_Bitacora` int       NOT NULL AUTO_INCREMENT,
    `CI_Id`          int       NOT NULL,
    `CV_DNI_Usuario` varchar(50)    DEFAULT NULL,
    `CV_Rol`         varchar(50)    DEFAULT NULL,
    `CV_Descripcion` varchar(50)    DEFAULT NULL,
    `CT_Fecha`       timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`CI_Id_Bitacora`, `CI_Id`),
    KEY `CI_Id` (`CI_Id`),
    CONSTRAINT `th_bitacoras_ibfk_1` FOREIGN KEY (`CI_Id`) REFERENCES `ta_usuarios` (`CI_Id`)
);


/*
 Tablas Complejas
 */

/*
 Tabla Organismo
 */

DROP TABLE IF EXISTS `ta_hechos_organismos`;
DROP TABLE IF EXISTS `ta_organismos`;
CREATE TABLE `ta_organismos`
(
    `CI_Id`             int         NOT NULL AUTO_INCREMENT,
    `CV_Nombre`         varchar(50) NOT NULL,
    `CV_Rol`            varchar(50) NOT NULL,
    `CV_Tipo_Organismo` varchar(50) NOT NULL,
    `CV_Nacionalidad`   varchar(50) NOT NULL,
    `CV_Contacto`       varchar(50) NOT NULL,
    PRIMARY KEY (`CI_Id`)
);

INSERT INTO `ta_organismos`
VALUES (1, 'Organismo1', 'Recopilador de datos', '1', 'Tica', '61159780'),
       (2, 'Organismo2', 'Consultar de datos', '2', 'Tica', '61159781');

/*
 Tabla Imputado
 */

DROP TABLE IF EXISTS `ta_hechos_imputados`;
DROP TABLE IF EXISTS `ta_imputados`;
CREATE TABLE IF NOT EXISTS `ta_imputados`
(
    `ci_id`                           int(11)      NOT NULL AUTO_INCREMENT,
    `cv_dni`                          varchar(50)  NOT NULL,
    `cv_nombre`                       varchar(50)  NOT NULL,
    `cv_genero`                       varchar(50)  NOT NULL,
    `cv_Orientacion_Sexual`           varchar(50)  NOT NULL,
    `cv_sexo`                         char(1)  NOT NULL,
    `ci_edad`                         int(50)      NOT NULL,
    `CV_Lugar_Nacimiento`             varchar(50)  NOT NULL,
    `cv_pais`                         varchar(20)  NOT NULL,
    `CV_Nacionalidad`                 varchar(25)  NOT NULL,
    `CV_Educacion`                    varchar(25)  NOT NULL,
    `CV_ocupacion`                    varchar(25)  NOT NULL,
    `CV_domicilio`                    varchar(255) NOT NULL,
    `CV_Lugar_Residencia`             varchar(50)  NOT NULL,
    `CV_Condicion_Migratoria`         varchar(25)  NOT NULL,
    `CV_Etnia`                        varchar(15)  NOT NULL,
    `CV_Situacion_Juridica`           varchar(30)  NOT NULL,
    `CV_Estado_Conyugal`              varchar(20)  NOT NULL,
    `CV_Permiso_Portacion_Armas`      varchar(3)   NOT NULL,
    `CV_Pertenencia_Fuerza_Seguridad` varchar(3)   NOT NULL,
    `CV_Antecedentes`                 varchar(255) NOT NULL,
    `CV_Suicidio`                     varchar(3)   NOT NULL,
    `CV_Generador`                    varchar(50)  NOT NULL,
    PRIMARY KEY (`ci_id`)
);

use femicidios;
INSERT INTO `ta_imputados` 
     (`ci_id`,  `cv_dni`, `cv_nombre`,          `cv_genero`, `cv_Orientacion_Sexual`, `cv_sexo`, `ci_edad`,`CV_Lugar_Nacimiento`, `cv_pais`, `CV_Nacionalidad`, `CV_Educacion`,         `CV_ocupacion`,         `CV_domicilio`, `CV_Lugar_Residencia`, `CV_Condicion_Migratoria`, `CV_Etnia`,`CV_Situacion_Juridica`, `CV_Estado_Conyugal`, `CV_Permiso_Portacion_Armas`, `CV_Pertenencia_Fuerza_Seguridad`, `CV_Antecedentes`, `CV_Suicidio`, `CV_Generador`)
VALUES (1,         '698', 'Yusei Fudo',          'Masculino', 'Heterosexual', 'M'    ,      60,   'Purgatorio',            'Japón', 'Japonés',       'Primaria', 'Agricultor',           'Japón',            'Panama',        'Iregular',           'Blanco',       'Imputado',               'Divorciado',         'SI',                          'SI',                            'NO',               'SI',       'Organismo1'),
       (2,         '699', 'Kijan Acuña Medrano', 'Masculino', 'Heterosexual', 'M'   ,      24,   'Purgatorio',           'Costa Rica','Costarricense',   'Primaria',                  'Carnicero',      'Costa Rica',       'Venezuela',        'Regular',             'Negro',        'Fugitivo',              'Soltero',            'NO',                          'NO',                            'SI',               'SI',       'Organismo2'),
       (3,       '700', 'Javier Acuña Corrales', 'Masculino', 'Heterosexual', 'M'  ,     60,    'Limón',              'Costa Rica','Costarricense',    'Doctorado',                 'Futbolista',     'Costa Rica',          'Canada',        'Regular',              'Blanco',      'Imputado',              'Casado',             'SI',                          'SI',                            'NO',               'SI',      'Organismo1'),
       (4,         '701', 'Yusei Fudo',          'Masculino', 'Heterosexual', 'M' ,       20,   'Purgatorio',          'Holanda', 'Holandes',           'Secundaria Alta',            'Soldado',       'Costa Rica',            'Peru',         'Asilado',              'Blanca',      'Imputado',              'Soltero',            'SI',                         'NO',                            'NO',               'NO',       'Organismo2'),
       (5,        '702',   'Jaden Yuki',        'Masculino', 'Heterosexual',    'F' ,      20, 'Academia de Duelos',      'Japón',     'Japones',          'Secundaria baja',            'Sensei',          'Japón',            'Japón'      ,'Regular',             'Asiatico',    'Indagado',              'Casado',             'SI',                         'SI',                            'SI',               'NO',       'Organismo1'),
       (6,          '1',     'Fulanito tal',     'Masculino', 'Heterosexual',  'M' ,         60,    'Ciudad Dominó',      'Japón',     'Japones',              'Maestria',                 'Sensei',          'Japón',            'Japón'       ,'Regular',             'Asiatico',    'Indagado',              'Casado',             'SI',                         'SI',                            'SI',               'NO',       'Organismo1');
   /*  (`ci_id`, `cv_dni`, `cv_nombre`,        `cv_genero`, `cv_Orientacion_Sexual`, `ci_edad,`CV_Lugar_Nacimiento`, `cv_pais`, `CV_Nacionalidad`,       `CV_Educacion`,            `CV_ocupacion`,   `CV_domicilio`, `CV_Lugar_Residencia`, `CV_Condicion_Migratoria`, `CV_Etnia`,`CV_Situacion_Juridica`, `CV_Estado_Conyugal`, `CV_Permiso_Portacion_Armas`,`CV_Pertenencia_Fuerza_Seguridad`, `CV_Antecedentes`, `CV_Suicidio`, `CV_Generador`)*/

     /*(`ci_id`, `cv_dni`, `cv_nombre`,        `cv_genero`, `cv_Orientacion_Sexual`, `ci_edad,`CV_Lugar_Nacimiento`, `cv_pais`, `CV_Nacionalidad`,       `CV_Educacion`,            `CV_ocupacion`,   `CV_domicilio`, `CV_Lugar_Residencia`, `CV_Condicion_Migratoria`, `CV_Etnia`,`CV_Situacion_Juridica`, `CV_Estado_Conyugal`, `CV_Permiso_Portacion_Armas`,`CV_Pertenencia_Fuerza_Seguridad`, `CV_Antecedentes`, `CV_Suicidio`, `CV_Generador`)*/

/*
 Tabla Hechos
 */

DROP TABLE IF EXISTS `ta_lugar`;
DROP TABLE IF EXISTS `ta_hechos`;
CREATE TABLE `ta_hechos`
(
    `CI_Id`              int        NOT NULL AUTO_INCREMENT,
    `CI_Tipo_Victima`    int        NOT NULL,
    `CI_Tipo_Relacion`   int        NOT NULL,
    `CI_Modalidad`       int        NOT NULL,
    `CI_Id_Victima`      int        NOT NULL,
    `CI_Id_Proceso`      int        NOT NULL,
    `CI_Pais`            int        NOT NULL,
    `CV_Provincia` varchar(20) NOT NULL,
    `CV_Canton` varchar(20) NOT NULL,
    `CV_Distrito` varchar(20) NOT NULL,
    `CV_Agresion_Sexual` varchar(2) NOT NULL,
    `CV_Denuncia_Previa` varchar(2) NOT NULL,
    `CI_Id_Generador`    int        NOT NULL,
    `CD_Fecha`           varchar(40)  DEFAULT NULL,
    `CV_Detalles`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`CI_Id`)
);

INSERT INTO `ta_hechos`
VALUES (1, 1, 1, 1, 1, 1, 1,'Limón', 'Limon','Limon', 'SI', 'SI', 1, '2023-06-02T21:54', 'Detalles1'),
       (2, 2, 2, 2, 2, 2, 2,'Limón', 'Guapiles','Roxana', 'NO', 'NO', 2,'2023-06-02T21:54', 'Detalles2');

/*
 Tabla Hecho - Organismo
 */

DROP TABLE IF EXISTS `ta_hechos_organismos`;
CREATE TABLE `ta_hechos_organismos`
(
    `CI_Id`        int NOT NULL AUTO_INCREMENT,
    `CI_Hecho`     int DEFAULT NULL,
    `CI_Organismo` int DEFAULT NULL,
    PRIMARY KEY (`CI_Id`),
    UNIQUE KEY `unique_hechos_organismos` (`CI_Hecho`, `CI_Organismo`),
    KEY `CI_Organismo` (`CI_Organismo`),
    CONSTRAINT `ta_hechos_organismos_ibfk_1` FOREIGN KEY (`CI_Hecho`) REFERENCES `ta_hechos` (`CI_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `ta_hechos_organismos_ibfk_2` FOREIGN KEY (`CI_Organismo`) REFERENCES `ta_organismos` (`CI_Id`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `ta_hechos_organismos`
VALUES (1, 1, 1),
       (2, 2, 2);

/*
 Tabla hecho - imputado
 */

DROP TABLE IF EXISTS `ta_hechos_imputados`;
CREATE TABLE `ta_hechos_imputados`
(
    `CI_Id`       int NOT NULL AUTO_INCREMENT,
    `CI_Hecho`    int DEFAULT NULL,
    `CI_Imputado` int DEFAULT NULL,
    PRIMARY KEY (`CI_Id`),
    UNIQUE KEY `unique_hechos_imputados` (`CI_Hecho`, `CI_Imputado`),
    KEY `CI_Imputado` (`CI_Imputado`),
    CONSTRAINT `ta_hechos_imputados_ibfk_1` FOREIGN KEY (`CI_Hecho`) REFERENCES `ta_hechos` (`CI_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `ta_hechos_imputados_ibfk_2` FOREIGN KEY (`CI_Imputado`) REFERENCES `ta_imputados` (`ci_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `ta_hechos_imputados`
VALUES (1, 1, 1),
       (2, 2, 2);

/*
    Tabla Lugar
*/


DROP TABLE IF EXISTS `ta_lugar`;
CREATE TABLE `ta_lugar`
(
    `CI_Codigo`      int          NOT NULL AUTO_INCREMENT,
    `CI_Hecho`       int          NOT NULL,
    `CV_Descripcion` varchar(100) NOT NULL,
    `CI_Tipo_Lugar`  int          NOT NULL,
    `CV_Direccion`   varchar(100) NOT NULL,
    `CV_Ciudad`      varchar(50)  NOT NULL,
    `CI_Pais`        int          NOT NULL,
    `CI_Codigo_Postal` int(15)  NOT NULL,
    PRIMARY KEY (`CI_Codigo`),
    KEY `CI_Hecho` (`CI_Hecho`),
    CONSTRAINT `ta_lugar_ibfk_1` FOREIGN KEY (`CI_Hecho`) REFERENCES `ta_hechos` (`CI_Id`)
);

INSERT INTO `ta_lugar`
VALUES (1, 1, 'Descripcion 1', 1, 'Direccion1', 'Ciudad1', 506,3030),
       (2, 1, 'Descripcion2', 2, 'Direccion2', 'Ciudad2', 506,3030),
       (3, 2, 'Descripcion1', 1, 'Direccion1', 'Ciudad1', 506,3030),
       (4, 2, 'Descripcion2', 2, 'Direccion2', 'Ciudad2', 506,3030);

/*
 Tabla ProcesoJudicial
 */


DROP TABLE IF EXISTS `ta_procesojudicial`;
CREATE TABLE `ta_procesojudicial`
(
    `CI_Id`                 int         NOT NULL AUTO_INCREMENT,
    `CV_Estado`             varchar(50) NOT NULL,
    `CD_Fecha_Apertura`     date        NOT NULL,
    `CI_Personas_Imputadas` int         NOT NULL,
    `CV_Agravantes`         varchar(50) DEFAULT NULL,
    `CV_Tipo_Delito`        varchar(30) DEFAULT NULL,
    PRIMARY KEY (`CI_Id`)
);

INSERT INTO `ta_procesojudicial`
VALUES (1, 'Completado',  '2023-06-02', 1, 'Agravante1', 'Homicidio'),
       (2, 'No Completado', '2023-06-02', 2, 'Agravante2',  'Homicidio Injustificado');




/*
 Tabla Victima
 */

/*VICTIMA*/

DROP TABLE IF EXISTS `TA_Victima`;
CREATE TABLE femicidios.TA_Victima
(

    CI_Id                   INT          NOT NULL AUTO_INCREMENT,
    CV_Dni                  VARCHAR(50)  NOT NULL,
    CV_Nombre               VARCHAR(50)  NOT NULL,
    CV_Apellidopaterno      VARCHAR(50)  NOT NULL,
    CV_Apellidomaterno      VARCHAR(50)  NOT NULL,
    CI_Edad                 int          NOT NULL,
    CV_Idgenero             int          NOT NULL,
    CV_Lugarnac             VARCHAR(100) NOT NULL,
    CV_Orientasex           int          NOT NULL,
    CV_Nacionalidad         VARCHAR(50)  NOT NULL,
    CI_Educacion            INT          NOT NULL,
    CV_Ocupacion            VARCHAR(50)  NOT NULL,
    CV_Domicilio            VARCHAR(100) NOT NULL,
    CV_Lugar_Residencia     VARCHAR(100) NOT NULL,
    CV_Discapacidad         VARCHAR(50)  NOT NULL,
    CV_Condicion_Migratoria VARCHAR(50)  NOT NULL,
    CV_Etnia                VARCHAR(50)  NOT NULL,
    CV_Medidas_Proteccion   VARCHAR(50)  NOT NULL,
    CV_Denuncias_Previas    VARCHAR(50)  NOT NULL,
    CI_Hijos                INT          NOT NULL,
    CV_Generador            VARCHAR(50)  NOT NULL,
    PRIMARY KEY (CI_Id)
);

insert into TA_Victima(CV_DNI, CV_Nombre, CV_ApellidoPaterno, CV_ApellidoMaterno, CI_Edad, CV_IDGenero, CV_LugarNac,
                       CV_OrientaSex, CV_Nacionalidad, CI_Educacion, CV_Ocupacion, CV_Domicilio, CV_Lugar_Residencia,
                       CV_Discapacidad, CV_Condicion_Migratoria, CV_Etnia, CV_Medidas_Proteccion, CV_Denuncias_Previas,
                       CI_Hijos, CV_Generador)
values (203450876, 'Albertina', 'Chill', 'Pepper', 30, 1, 'Cartago', 1, 'Costarricense', 1, 'Cruz Rojista',
        'Limon centro', 'Limon', 'Ninguna', 'Todo bien', 'Mestizo', 'Ninguna', 'Ninguna', 0, 'Nada'),
       (203450877, 'Albertino', 'Chill', 'Pepper', 30, 2, 'Cartago', 2, 'Costarricense', 2, 'Cruz Rojista',
        'Limon centro', 'Limon', 'Ninguna', 'Todo bien', 'Mestizo', 'Ninguna', 'Ninguna', 0, 'Nada');
        

        DROP TABLE IF EXISTS TA_Dependiente;
CREATE TABLE TA_Dependiente
(
    CI_Codigo        int         NOT NULL AUTO_INCREMENT,
    CV_Dni    VARCHAR(50)  NOT NULL,
    CI_Tiporelacion   int DEFAULT NULL,
    
  
    PRIMARY KEY (CI_Codigo)
  
);

insert into ta_dependiente(CV_Dni, CI_Tiporelacion)
values (504510111,2),(601230321,3),(104510111,2),(401230321,3),(201110111,2),(501270351,3)
;

DROP TABLE IF EXISTS Ta_TipoRelacionFamiliar;
Create Table Ta_TipoRelacionFamiliar
(
    CI_Codigo        int         NOT NULL AUTO_INCREMENT,
    CV_Titulo    VARCHAR(50)  NOT NULL,
    CV_Descripcion   VARCHAR(255) DEFAULT NULL,
    
    PRIMARY KEY (CI_Codigo)
  
);
 insert into ta_tiporelacionfamiliar(CI_Codigo,CV_Titulo,CV_Descripcion) values(10,"Hermanos y hermanas","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar (CV_Titulo,CV_Descripcion) values("Abuelos y nietos.","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar (CV_Titulo,CV_Descripcion)  values("Tíos y sobrinos.","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar (CV_Titulo,CV_Descripcion)  values("Cónyuge/esposo(a) y
esposa(o).","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar (CV_Titulo,CV_Descripcion)  values("Cuñados y cuñadas.","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar  (CV_Titulo,CV_Descripcion)  values("Sobrinos nietos.","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar  (CV_Titulo,CV_Descripcion)  values("Bisabuelos y bisnietos.","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar  (CV_Titulo,CV_Descripcion)  values("Conocido","Tipo de relación que posee");
insert into ta_tiporelacionfamiliar  (CV_Titulo,CV_Descripcion)  values("Amigo","Tipo de relación que posee");
