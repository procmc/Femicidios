CREATE Database femicidios;

/*Identidad de Genero*/

use femicidios; 
    create table identidadesGenerosEstandar(
    id int	NOT NULL AUTO_INCREMENT,
    nombre varchar(55) not null,
    descripcion varchar(255) not null,
    primary key clustered (id ASC)
    );
    use femicidios; 
    
      /* Id	int	NOT NULL AUTO_INCREMEidentidadesgenerosestandaridentidadesgenerosestandarNT,*/
      /*primary key clustered (Id ASC)*/
    create table codigoPaises(
    spanish	varchar (60) NOT NULL,
	english varchar (60) NOT NULL,
    ISO2 varchar (3),
    ISO3 varchar (5),
    telefonoPais varchar(5) 
    );
ALTER TABLE codigoPaises ADD Id INT AUTO_INCREMENT PRIMARY KEY;
    use femcidios;

create table identidadGenero(
	Id	int	NOT NULL AUTO_INCREMENT,
    cedula	varchar (50) NOT NULL,
	genero int not null,
    codigoPais int not null,
	primary key clustered (Id ASC),
	FOREIGN KEY (codigoPais) REFERENCES codigoPaises(Id),
	FOREIGN KEY (genero) REFERENCES identidadesGenerosEstandar(id)
    );
 use femicidios; 

 /*INGRESO DE CAMPOS CODIFICADOS*/

 INSERT INTO identidadesGenerosEstandar (id, nombre,descripcion)
      VALUES (1,'Cis Hombre', 'Hombre identificado con el género que se le asignó al nacer.'),
			 (2,'Cis Mujer', 'Mujer identificado con el género que se le asignó al nacer.'),
             (3,'No Binario', '	Persona no identificada con género masculino o femenino.'),
             (4,'Otro', 'Otra identidad de género.'),
             (5,'Se Desconoce', '	Se desconoce el género.'),
             (6,'Mujer Trans', 'Mujer que no se identifica con el género que se le asignó al nacer.'),
             (7,'Hombre Trans', 'Hombre que no se identifica con el género que se le asignó al nacer.');
         


     INSERT INTO codigopaises VALUES ('Afganistán','Afghanistan','AF','AFG','93',1),('Albania','Albania','AL','ALB','355',2),('Alemania','Germany','DE','DEU','49',3),('Algeria','Algeria','DZ','DZA','213',4),('Andorra','Andorra','AD','AND','376',5),('Angola','Angola','AO','AGO','244',6),('Anguila','Anguilla','AI','AIA','1 264',7),('Antártida','Antarctica','AQ','ATA','672',8),('Antigua y Barbuda','Antigua and Barbuda','AG','ATG','1 268',9),('Antillas Neerlandesas','Netherlands Antilles','AN','ANT','599',10),('Arabia Saudita','Saudi Arabia','SA','SAU','966',11),('Argentina','Argentina','AR','ARG','54',12),('Armenia','Armenia','AM','ARM','374',13),('Aruba','Aruba','AW','ABW','297',14),('Australia','Australia','AU','AUS','61',15),('Austria','Austria','AT','AUT','43',16),('Azerbayán','Azerbaijan','AZ','AZE','994',17),('Bahamas','Bahamas','BS','BHS','1 242',18),('Bahrein','Bahrain','BH','BHR','973',19),('Bangladesh','Bangladesh','BD','BGD','880',20),('Barbados','Barbados','BB','BRB','1 246',21),('Bélgica','Belgium','BE','BEL','32',22),('Belice','Belize','BZ','BLZ','501',23),('Benín','Benin','BJ','BEN','229',24),('Bhután','Bhutan','BT','BTN','975',25),('Bielorrusia','Belarus','BY','BLR','375',26),('Birmania','Myanmar','MM','MMR','95',27),('Bolivia','Bolivia','BO','BOL','591',28),('Bosnia y Herzegovina','Bosnia and Herzegovina','BA','BIH','387',29),('Botsuana','Botswana','BW','BWA','267',30),('Brasil','Brazil','BR','BRA','55',31),('Brunéi','Brunei','BN','BRN','673',32),('Bulgaria','Bulgaria','BG','BGR','359',33),('Burkina Faso','Burkina Faso','BF','BFA','226',34),('Burundi','Burundi','BI','BDI','257',35),('Cabo Verde','Cape Verde','CV','CPV','238',36),('Camboya','Cambodia','KH','KHM','855',37),('Camerún','Cameroon','CM','CMR','237',38),('Canadá','Canada','CA','CAN','1',39),('Chad','Chad','TD','TCD','235',40),('Chile','Chile','CL','CHL','56',41),('China','China','CN','CHN','86',42),('Chipre','Cyprus','CY','CYP','357',43),('Ciudad del Vaticano','Vatican City State','VA','VAT','39',44),('Colombia','Colombia','CO','COL','57',45),('Comoras','Comoros','KM','COM','269',46),('Congo','Congo','CG','COG','242',47),('Congo','Congo','CD','COD','243',48),('Corea del Norte','North Korea','KP','PRK','850',49),('Corea del Sur','South Korea','KR','KOR','82',50),('Costa de Marfil','Ivory Coast','CI','CIV','225',51),('Costa Rica','Costa Rica','CR','CRI','506',52),('Croacia','Croatia','HR','HRV','385',53),('Cuba','Cuba','CU','CUB','53',54),('Dinamarca','Denmark','DK','DNK','45',55),('Dominica','Dominica','DM','DMA','1 767',56),('Ecuador','Ecuador','EC','ECU','593',57),('Egipto','Egypt','EG','EGY','20',58),('El Salvador','El Salvador','SV','SLV','503',59),('Emiratos Árabes Unidos','United Arab Emirates','AE','ARE','971',60),('Eritrea','Eritrea','ER','ERI','291',61),('Escocia','Scotland','GB','','44',62),('Eslovaquia','Slovakia','SK','SVK','421',63),('Eslovenia','Slovenia','SI','SVN','386',64),('España','Spain','ES','ESP','34',65),('Estados Unidos de América','United States of America','US','USA','1',66),('Estonia','Estonia','EE','EST','372',67),('Etiopía','Ethiopia','ET','ETH','251',68),('Filipinas','Philippines','PH','PHL','63',69),('Finlandia','Finland','FI','FIN','358',70),('Fiyi','Fiji','FJ','FJI','679',71),('Francia','France','FR','FRA','33',72),('Gabón','Gabon','GA','GAB','241',73),('Gales','Wales','GB','','44',74),('Gambia','Gambia','GM','GMB','220',75),('Georgia','Georgia','GE','GEO','995',76),('Ghana','Ghana','GH','GHA','233',77),('Gibraltar','Gibraltar','GI','GIB','350',78),('Granada','Grenada','GD','GRD','1 473',79),('Grecia','Greece','GR','GRC','30',80),('Groenlandia','Greenland','GL','GRL','299',81),('Guadalupe','Guadeloupe','GP','GLP','',82),('Guam','Guam','GU','GUM','1 671',83),('Guatemala','Guatemala','GT','GTM','502',84),('Guayana Francesa','French Guiana','GF','GUF','',85),('Guernsey','Guernsey','GG','GGY','',86),('Guinea','Guinea','GN','GIN','224',87),('Guinea Ecuatorial','Equatorial Guinea','GQ','GNQ','240',88),('Guinea-Bissau','Guinea-Bissau','GW','GNB','245',89),('Guyana','Guyana','GY','GUY','592',90),('Haití','Haiti','HT','HTI','509',91),('Honduras','Honduras','HN','HND','504',92),('Hong kong','Hong Kong','HK','HKG','852',93),('Hungría','Hungary','HU','HUN','36',94),('India','India','IN','IND','91',95),('Indonesia','Indonesia','ID','IDN','62',96),('Inglaterra','England','GB','','44',97),('Irak','Iraq','IQ','IRQ','964',98),('Irán','Iran','IR','IRN','98',99),('Irlanda','Ireland','IE','IRL','353',100),('Irlanda del Norte','Northern Irland','GB','','44',101),('Isla Bouvet','Bouvet Island','BV','BVT','',102),('Isla de Man','Isle of Man','IM','IMN','44',103),('Isla de Navidad','Christmas Island','CX','CXR','61',104),('Isla Norfolk','Norfolk Island','NF','NFK','',105),('Islandia','Iceland','IS','ISL','354',106),('Islas Bermudas','Bermuda Islands','BM','BMU','1 441',107),('Islas Caimán','Cayman Islands','KY','CYM','1 345',108),('Islas Cocos (Keeling)','Cocos (Keeling) Islands','CC','CCK','61',109),('Islas Cook','Cook Islands','CK','COK','682',110),('Islas de Åland','Åland Islands','AX','ALA','',111),('Islas Feroe','Faroe Islands','FO','FRO','298',112),('Islas Georgias del Sur y Sandwich del Sur','South Georgia and the South Sandwich Islands','GS','SGS','',113),('Islas Heard y McDonald','Heard Island and McDonald Islands','HM','HMD','',114),('Islas Maldivas','Maldives','MV','MDV','960',115),('Islas Malvinas','Falkland Islands (Malvinas)','FK','FLK','500',116),('Islas Marianas del Norte','Northern Mariana Islands','MP','MNP','1 670',117),('Islas Marshall','Marshall Islands','MH','MHL','692',118),('Islas Pitcairn','Pitcairn Islands','PN','PCN','870',119),('Islas Salomón','Solomon Islands','SB','SLB','677',120),('Islas Turcas y Caicos','Turks and Caicos Islands','TC','TCA','1 649',121),('Islas Ultramarinas Menores de Estados Unidos','United States Minor Outlying Islands','UM','UMI','',122),('Islas Vírgenes Británicas','Virgin Islands','VG','VG','1 284',123),('Islas Vírgenes de los Estados Unidos','United States Virgin Islands','VI','VIR','1 340',124),('Israel','Israel','IL','ISR','972',125),('Italia','Italy','IT','ITA','39',126),('Jamaica','Jamaica','JM','JAM','1 876',127),('Japón','Japan','JP','JPN','81',128),('Jersey','Jersey','JE','JEY','',129),('Jordania','Jordan','JO','JOR','962',130),('Kazajistán','Kazakhstan','KZ','KAZ','7',131),('Kenia','Kenya','KE','KEN','254',132),('Kirgizstán','Kyrgyzstan','KG','KGZ','996',133),('Kiribati','Kiribati','KI','KIR','686',134),('Kuwait','Kuwait','KW','KWT','965',135),('Laos','Laos','LA','LAO','856',136),('Lesoto','Lesotho','LS','LSO','266',137),('Letonia','Latvia','LV','LVA','371',138),('Líbano','Lebanon','LB','LBN','961',139),('Liberia','Liberia','LR','LBR','231',140),('Libia','Libya','LY','LBY','218',141),('Liechtenstein','Liechtenstein','LI','LIE','423',142),('Lituania','Lithuania','LT','LTU','370',143),('Luxemburgo','Luxembourg','LU','LUX','352',144),('Macao','Macao','MO','MAC','853',145),('Macedônia','Macedonia','MK','MKD','389',146),('Madagascar','Madagascar','MG','MDG','261',147),('Malasia','Malaysia','MY','MYS','60',148),('Malawi','Malawi','MW','MWI','265',149),('Mali','Mali','ML','MLI','223',150),('Malta','Malta','MT','MLT','356',151),('Marruecos','Morocco','MA','MAR','212',152),('Martinica','Martinique','MQ','MTQ','',153),('Mauricio','Mauritius','MU','MUS','230',154),('Mauritania','Mauritania','MR','MRT','222',155),('Mayotte','Mayotte','YT','MYT','262',156),('México','Mexico','MX','MEX','52',157),('Micronesia','Estados Federados de','FM','FSM','691',158),('Moldavia','Moldova','MD','MDA','373',159),('Mónaco','Monaco','MC','MCO','377',160),('Mongolia','Mongolia','MN','MNG','976',161),('Montenegro','Montenegro','ME','MNE','382',162),('Montserrat','Montserrat','MS','MSR','1 664',163),('Mozambique','Mozambique','MZ','MOZ','258',164),('Namibia','Namibia','NA','NAM','264',165),('Nauru','Nauru','NR','NRU','674',166),('Nepal','Nepal','NP','NPL','977',167),('Nicaragua','Nicaragua','NI','NIC','505',168),('Niger','Niger','NE','NER','227',169),('Nigeria','Nigeria','NG','NGA','234',170),('Niue','Niue','NU','NIU','683',171),('Noruega','Norway','NO','NOR','47',172),('Nueva Caledonia','New Caledonia','NC','NCL','687',173),('Nueva Zelanda','New Zealand','NZ','NZL','64',174),('Omán','Oman','OM','OMN','968',175),('Países Bajos','Netherlands','NL','NLD','31',176),('Pakistán','Pakistan','PK','PAK','92',177),('Palau','Palau','PW','PLW','680',178),('Palestina','Palestine','PS','PSE','',179),('Panamá','Panama','PA','PAN','507',180),('Papúa Nueva Guinea','Papua New Guinea','PG','PNG','675',181),('Paraguay','Paraguay','PY','PRY','595',182),('Perú','Peru','PE','PER','51',183),('Polinesia Francesa','French Polynesia','PF','PYF','689',184),('Polonia','Poland','PL','POL','48',185),('Portugal','Portugal','PT','PRT','351',186),('Puerto Rico','Puerto Rico','PR','PRI','1',187),('Qatar','Qatar','QA','QAT','974',188),('Reino Unido','United Kingdom','GB','GBR','44',189),('República Centroafricana','Central African Republic','CF','CAF','236',190),('República Checa','Czech Republic','CZ','CZE','420',191),('República Dominicana','Dominican Republic','DO','DOM','1 809',192),('Reunión','Réunion','RE','REU','',193),('Ruanda','Rwanda','RW','RWA','250',194),('Rumanía','Romania','RO','ROU','40',195),('Rusia','Russia','RU','RUS','7',196),('Sahara Occidental','Western Sahara','EH','ESH','',197),('Samoa','Samoa','WS','WSM','685',198),('Samoa Americana','American Samoa','AS','ASM','1 684',199),('San Bartolomé','Saint Barthélemy','BL','BLM','590',200),('San Cristóbal y Nieves','Saint Kitts and Nevis','KN','KNA','1 869',201),('San Marino','San Marino','SM','SMR','378',202),('San Martín (Francia)','Saint Martin (French part)','MF','MAF','1 599',203),('San Pedro y Miquelón','Saint Pierre and Miquelon','PM','SPM','508',204),('San Vicente y las Granadinas','Saint Vincent and the Grenadines','VC','VCT','1 784',205),('Santa Elena','Ascensión y Tristán de Acuña','SH','SHN','290',206),('Santa Lucía','Saint Lucia','LC','LCA','1 758',207),('Santo Tomé y Príncipe','Sao Tome and Principe','ST','STP','239',208),('Senegal','Senegal','SN','SEN','221',209),('Serbia','Serbia','RS','SRB','381',210),('Seychelles','Seychelles','SC','SYC','248',211),('Sierra Leona','Sierra Leone','SL','SLE','232',212),('Singapur','Singapore','SG','SGP','65',213),('Siria','Syria','SY','SYR','963',214),('Somalia','Somalia','SO','SOM','252',215),('Sri lanka','Sri Lanka','LK','LKA','94',216),('Sudáfrica','South Africa','ZA','ZAF','27',217),('Sudán','Sudan','SD','SDN','249',218),('Suecia','Sweden','SE','SWE','46',219),('Suiza','Switzerland','CH','CHE','41',220),('Surinám','Suriname','SR','SUR','597',221),('Svalbard y Jan Mayen','Svalbard and Jan Mayen','SJ','SJM','',222),('Swazilandia','Swaziland','SZ','SWZ','268',223),('Tadjikistán','Tajikistan','TJ','TJK','992',224),('Tailandia','Thailand','TH','THA','66',225),('Taiwán','Taiwan','TW','TWN','886',226),('Tanzania','Tanzania','TZ','TZA','255',227),('Territorio Británico del Océano Índico','British Indian Ocean Territory','IO','IOT','',228),('Territorios Australes y Antárticas Franceses','French Southern Territories','TF','ATF','',229),('Timor Oriental','East Timor','TL','TLS','670',230),('Togo','Togo','TG','TGO','228',231),('Tokelau','Tokelau','TK','TKL','690',232),('Tonga','Tonga','TO','TON','676',233),('Trinidad y Tobago','Trinidad and Tobago','TT','TTO','1 868',234),('Tunez','Tunisia','TN','TUN','216',235),('Turkmenistán','Turkmenistan','TM','TKM','993',236),('Turquía','Turkey','TR','TUR','90',237),('Tuvalu','Tuvalu','TV','TUV','688',238),('Ucrania','Ukraine','UA','UKR','380',239),('Uganda','Uganda','UG','UGA','256',240),('Uruguay','Uruguay','UY','URY','598',241),('Uzbekistán','Uzbekistan','UZ','UZB','998',242),('Vanuatu','Vanuatu','VU','VUT','678',243),('Venezuela','Venezuela','VE','VEN','58',244),('Vietnam','Vietnam','VN','VNM','84',245),('Wallis y Futuna','Wallis and Futuna','WF','WLF','681',246),('Yemen','Yemen','YE','YEM','967',247),('Yibuti','Djibouti','DJ','DJI','253',248),('Zambia','Zambia','ZM','ZMB','260',249),('Zimbabue','Zimbabwe','ZW','ZWE','263',250);    



   
 
/*********************************************************************************************************/
 


 
/* PERFILES TABLA*/
use femicidios; 
CREATE TABLE femicidios.TA_perfil ( 

CI_Id INTEGER NOT NULL AUTO_INCREMENT, 

CV_Descripcion  VARCHAR (50) NOT NULL, 

CV_rol VARCHAR (50) NOT NULL, 

PRIMARY KEY (CI_Id) 

); 



/*Insercion de roles*/ 

INSERT INTO `femicidios`.`ta_perfil` 

( 

`CV_Descripcion`, 

`CV_rol`) 

VALUES 

("david1", 

"Administrador"); 

  

INSERT INTO `femicidios`.`ta_perfil` 

( 

`CV_Nombre`, 

`CV_rol`) 

VALUES 

("David2", 

"Convencional"); 

  

INSERT INTO `femicidios`.`ta_perfil` 

( 

`CV_Nombre`, 

`CV_rol`) 

VALUES 

("David3", 

"Consultor"); 
/*********************************************************************************************************/

/*HECHOS */
use femicidios; 
CREATE TABLE femicidios.TA_Hechos (  
CI_Id INT NOT NULL AUTO_INCREMENT,  
CI_Tipo_Victima INT NOT NULL,  
CI_Tipo_Relacion INT NOT NULL,  
CI_Modalidad INT NOT NULL,  
CI_Pais	INT NOT NULL, 
PRIMARY KEY (CI_Id)  
); 
INSERT INTO `femicidios`.`ta_hechos`  
(  
`CI_Tipo_Victima`,  
`CI_Tipo_Relacion`,  
`CI_Modalidad` , 
`CI_Pais` 
)  
VALUES  
(  
1,  
1,  
1, 
506); 
/*********************************************************************************************************/

/* CREACION DE LUGAR*/

CREATE table femicidios.TA_Lugar( 

CI_Codigo int NOT NULL AUTO_INCREMENT, 

CIF_idHecho int NOT NULL, 

CV_Descripcion Varchar(100) NOT NULL, 

CIF_Tipo_Lugar int NOT NULL, 

CV_Direccion Varchar(100) NOT NULL, 

CV_Ciudad Varchar(50) NOT NULL, 

CV_Pais int NOT NULL, 

Primary key (CI_Codigo), 

foreign key(CIF_idHecho) REFERENCES femicidios.TA_Hechos(CI_Id) 

); 

DROP TABLE IF EXISTS `ta_lugar`; 
/*LUGAR*/
CREATE TABLE `ta_lugar` ( 

  `CI_Codigo` int NOT NULL AUTO_INCREMENT, 

  `CI_Hecho` int NOT NULL, 

  `CV_Descripcion` varchar(100) NOT NULL, 

  `CI_Tipo_Lugar` int NOT NULL, 

  `CV_Direccion` varchar(100) NOT NULL, 

  `CV_Ciudad` varchar(50) NOT NULL, 

  `CI_Pais` int NOT NULL, 

  PRIMARY KEY (`CI_Codigo`), 

  KEY `CI_Hecho` (`CI_Hecho`), 

  CONSTRAINT `ta_lugar_ibfk_1` FOREIGN KEY (`CI_Hecho`) REFERENCES `ta_hechos` (`CI_Id`) 

); 

/*********************************************************************************************************/
/* IMPUTADOS */
create table ta_imputados( 

    ci_id int not null auto_increment, 

    cv_dni varchar(50) not null, 

    cv_nombre varchar (50) not null, 

    cv_genero varchar (50) not null, 

    cv_orientacionSexual varchar (50) not null, 

    ci_edad int (50) not null, 

    cv_lugarNacimiento varchar (50) not null, 

    cv_pais varchar (20) not null, 

    PRIMARY KEY(ci_id));
    
/*********************************************************************************************************/
    /*
    VICTIMA    */
    /*ORINTACION SEXUAL*/
CREATE TABLE femicidios.TA_OrientacionSexual ( 

CI_Codigo INT NOT NULL AUTO_INCREMENT, 

CV_Titulo VARCHAR (20) NOT NULL, 

CV_Descripcion VARCHAR (50) NOT NULL, 

PRIMARY KEY (CI_Codigo) 

); 

/*********************************************************************************************************/
/*VICTIMA*/

CREATE TABLE femicidios.TA_Victima ( 

CI_Id INT NOT NULL AUTO_INCREMENT, 

CV_Dni INT NOT NULL , 

CV_Nombre VARCHAR (50) NOT NULL, 

CV_Apellidopaterno VARCHAR (50) NOT NULL, 

CV_Apellidomaterno VARCHAR (50) NOT NULL, 

CV_Edad int  NOT NULL, 

CV_Idgenero int NOT NULL, 

CV_Lugarnac VARCHAR (100) NOT NULL, 

CV_Orientasex int NOT NULL, 
PRIMARY KEY (CI_Id) 

); 

insert into TA_Victima( 

CV_DNI, 

CV_Nombre, 

CV_ApellidoPaterno, 

CV_ApellidoMaterno, 

CV_Edad , 

CV_IDGenero, 

CV_LugarNac, 

CV_OrientaSex) values("601123245","Albertina","Alvarado","Casas",34,7,"La comarca",1); 

INSERT INTO ta_orientacionsexual(CI_Codigo,CV_Titulo,CV_Descripcion) 

VALUES (1,"Lesbiana","Se reconoce lesbiana."), 

(2,"Gay","Se reconoce gay."), 

(3,"Heterosexual","Se reconoce heterosexual."), 

(4,"Bisexual","Se reconoce bisexual."), 

(5,"Asexual","Se reconoce asexual."), 

(6,"Otros","Otra orientación sexual."), 

(7,"Se Desconoce","	Se desconoce orientación sexual.");

/*********************************************************************************************************/
/* ORGANISMO*/

CREATE TABLE femicidios.TA_Organismos ( 

CI_Id INT NOT NULL AUTO_INCREMENT, 

CV_Nombre VARCHAR (50) NOT NULL, 

CV_Rol VARCHAR (50) NOT NULL, 

CV_Tipo_Organismo VARCHAR (50) NOT NULL, 

CV_Nacionalidad VARCHAR (50) NOT NULL, 

CV_Contacto VARCHAR (50) NOT NULL, 

  

PRIMARY KEY (CI_Id) 

); 
INSERT INTO TA_Organismos

( 

`CV_Nombre`, 

`CV_Rol`, 

`CV_Tipo_Organismo`, 

`CV_Nacionalidad`, 

`CV_Contacto`) 

VALUES 

( 

'Organismo', 

'Admin', 

'A', 

'Costarrikeno', 

'666') ;

/*********************************************************************************************************/
  
  /*TIPO DE VICTIMA*/

CREATE TABLE `ta_tipovictima` ( 

  `CI_Codigo` int NOT NULL AUTO_INCREMENT, 

  `CV_Titulo` varchar(50) NOT NULL, 

  `CV_Descripcion` varchar(100) DEFAULT NULL, 

  PRIMARY KEY (`CI_Codigo`) 

) ;


INSERT INTO `ta_tipovictima` VALUES (1,'Principal','La victima principal del femicidio'),(2,'Vinculado','Victima vinculada al crimen principal'); 
/*MODALIDAD  */


CREATE TABLE `ta_modalidades` ( 

  `CI_Codigo` int NOT NULL AUTO_INCREMENT, 

  `CV_Titulo` varchar(50) NOT NULL, 

  `CV_Descripcion` varchar(50) NOT NULL, 

  PRIMARY KEY (`CI_Codigo`) 

);

INSERT INTO ta_modalidades (`CV_Titulo`,`CV_Descripcion`) 

VALUES 

('Golpes','Asesinada por golpes'), 

('Disparo de bala','Asesinada por disparo de bala'), 

('Apuñalamiento','Asesinada por apuñalamiento'), 

('Quemaduras','Asesinada por quemaduras'), 

('Asfixia','Asesinada por asfixia.'), 

('Ahogamiento','Asesinada por ahogamiento'), 

('Ataladramiento','Asesinada por ataladramiento'), 

('Atropellamiento','Asesinada por atropellamiento'), 

('Estrangulamiento','Asesinada por estrangulamiento'), 

('Otros medios','Asesinada por otros medios'), 

('Se Desconoce','Se desconoce la modalidad'); 

/*********************************************************************************************************/
/* NIVEL EDUCATIVO */

CREATE TABLE femicidios.TA_NivelEducativo( 

CI_Id INT NOT NULL AUTO_INCREMENT, 

CV_Titulo VARCHAR (50) NOT NULL, 

CV_Descripcion VARCHAR (75) NOT NULL, 

CI_Pais INT NOT NULL, 

PRIMARY KEY(CI_Id) 

); 

INSERT INTO ta_niveleducativo 

( 

`CV_Titulo`, 

`CV_Descripcion`, 

`CI_Pais`) 

VALUES 

("Primera infancia","Eduacion de la primera infancia.",506),  

  

( "Preescolar","Menos que primaria.",506),  

  

("Primaria","Educaciòn primaria",506),  

  

("Secundaria baja","Educaciòn secundaria baja", 506),  

  

("Secundaria alta","Educaciòn secundaria alta",506),  

  

("Postsecundaria no terciaria","Educaciòn postsecundaria no terciaria",506),  

  

("Terciaria", "Educaciòn terciaria de ciclo corto", 506),  

  

("Grado","Grado en educaciòn universitaria o nivel equivalente.",506),  

  

("Maestria","Nivel de maestrìa especializaciòn o equivalente",506),  

  

("Doctorado","Nivel de doctorado o equivalente", 506),  

  

("Se desconoce","Se desconoce nivel eduactivo",506); 

CREATE TABLE femicidios.TA_OrientacionesSexuales ( 

CI_Codigo INT NOT NULL AUTO_INCREMENT, 

CV_Titulo VARCHAR (50) NOT NULL, 

CV_Descripcion VARCHAR (50) NOT NULL, 

  

PRIMARY KEY (CI_Codigo) 

); 

 CREATE TABLE `ta_tipolugar` ( 

  `CI_Codigo` int NOT NULL AUTO_INCREMENT, 

  `CV_Titulo` varchar(30) NOT NULL, 

  `CV_Descripcion` varchar(80) DEFAULT NULL, 

  PRIMARY KEY (`CI_Codigo`) 

);

INSERT INTO ta_tipolugar 

(`CV_Titulo`, 

`CV_Descripcion`) 

VALUES 

('Domicilio Víctima' 

, 'Lugar de residencia particular de la víctima'), 

('Domicilio Víctimario' 

, 'Lugar de residencia particular del víctimario'), 

('Domicilio Particular' 

, 'Lugar de residencia no necesariamente de la víctima o victimario'), 

('Espacio Abierto' 

, 'Calle o transporte público'), 

('Puesto de Trabajo' 

, 'Lugar en el que se desempeñan sus tareas laborales habituales '), 

('Institución Educativa' 

, 'Escuelas u otras instituciones educativas'), 

('Prisión' 

, 'Instituciones penales o correccionales'), 

('Institución' 

, 'Entornos de atención institucional (incluye: hospitales, intituciones)'), 

('Otro' 

, ''), 

('Se desconoce' 

, ''); 

/*********************************************************************************************************/
/*  TIPO DE RELACIÓN*/

CREATE TABLE `ta_tiporelacion` ( 

  `CI_Codigo` int NOT NULL AUTO_INCREMENT, 

  `CV_Titulo` varchar(50) NOT NULL, 

  `CV_Descripcion` varchar(50) NOT NULL, 

  PRIMARY KEY (`CI_Codigo`) 

) ;

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Padre', 

'Victimario es padre de la víctima'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Madre', 

'Victimario es madre de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Padrastro', 

'Victimario es padrastro de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Madastra', 

'Victimario es madrastra de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Tutor/a', 

'Victimario es tutor/a de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Esposo/a', 

'Victimario es esposo/a de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Concubino/a', 

'Victimario es concubino/a de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Novio/a', 

'Victimario es novio/a de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Amante', 

'Victimario es amante de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Pareja Anterior', 

'Pareja o cónyuge anterior.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Pariente', 

'Victimario es pariente consanguíneo de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Laboral', 

'Victimario es empleado/a o colega de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Conocido/a', 

'Victimario es conocido/a de la víctima.'); 

  

INSERT INTO ta_tiporelacion

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Amigo/a', 

'Amigo/a de la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Otro Transgresor', 

'Otro transgresor conocido por la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Autoridad', 

'Por autoridades oficiales'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Desconocido/a', 

'Victimario no es conocido/a por la víctima.'); 

  

INSERT INTO `femicidios`.`ta_tiporelacion` 

( 

`CV_titulo`, 

`CV_descripcion`) 

VALUES 

('Se desconoce', 

'Se desconoce el tipo de relación.'); 
/*********************************************************************************************************/
CREATE TABLE femicidios. ta_tipoorganismo (  

CI_Codigo INT NOT NULL AUTO_INCREMENT,  

CV_Titulo VARCHAR(80) NOT NULL,  

CV_Descripcion VARCHAR(80),  

PRIMARY KEY (CI_Codigo)  

);  
/*Tipo Organismo*/


INSERT INTO `femicidios`.`ta_tipoorganismo`  

(`CV_Titulo`,  

 `CV_Descripcion`)  

VALUES  

('Policial'  

, 'Organismo policial incluyendo servicios de emergencia'),  

('Judicial'  

, 'Organismo Judicial a nivel nacional'),   

('Fiscalia'  

, 'Fiscalia'),  

('Salud'  

, 'Institucion de Salud'),  

('Ministerio de Desarrollo Social'  

, 'Ministerio de Desarrollo Social'),  

('Justicia Provincial'  

, 'Poder Judicial a nivel provincial'),  

('Ministerio Publico Fiscal'  

, ' '),  

('Ministerio de Justicia'  

, 'Ministerio de Justicia'),  

('Medicina'  

, 'Medicina Legal o Forense'),  

('Secretaria de la Mujer'  

, 'Ministerio o Secretaria de la Mujer'),  

('Otro'  

, ''),  

('Se desconoce'  

, ''); 
