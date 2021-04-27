CREATE TYPE "state" AS ENUM ('CONFIRM_WAIT', 'CONFIRMED', 'POSTED', 'PAY_WAIT', 'PAYED', 'REJECTED', 'POSTED_EXTRA', 'GOT_RESPONSE', 'ABORTED', 'SUCCESS', 'FINISHED');

CREATE TYPE "type" AS ENUM ('LOST', 'FOUND');

CREATE TYPE "role" AS ENUM ('ADMIN','OWNER','VOLUNTEER','RESPONDER','UNAUTHORISED');

CREATE TABLE "user"
(
    "email"     varchar PRIMARY KEY,
    "role"      role,
    "password"  varchar,
    "token"     varchar,
    "name"      varchar,
    "phone"     varchar,
    "isBlocked" boolean,
    "isActual" boolean,	
);

CREATE TABLE "userRole"
(
    "role"        role PRIMARY KEY,
    "description" varchar,
    "permissions" integer
);

CREATE TABLE "actor"
(
    "email" varchar PRIMARY KEY,
    "role"  role,
    "token" varchar
);

CREATE TABLE "publication"
(
    "id"          bigserial PRIMARY KEY,
    "email"       varchar,
    "description" varchar,
    "photo"       varchar,
    "state"       state,
    "createdAt"   bigint
);

CREATE TABLE "response"
(
    "id"            bigserial PRIMARY KEY,
    "email"         varchar,
    "publicationId" bigint
    "photo"         varchar,
    "description"   varchar
);

CREATE TABLE "extraType"
(
    "type"        type PRIMARY KEY,
    "description" varchar,
    "price"       integer
);

CREATE TABLE "publicationState"
(
    "state"       state PRIMARY KEY,
    "description" varchar
);

CREATE TABLE "extra"
(
    "id"            bigserial PRIMARY KEY,
    "publicationId" bigint
    "startDate"     date,
    "daysQuantity"  integer,
    "coordinates"   varchar,
    "type"          type,
    "paymentId"     bigint,
    "isPublishing"  boolean
);

CREATE TABLE "payment"
(
    "extraId"       bigint PRIMARY KEY,
    "amount"        integer,
    "transactionId" bigint
    "dateTime"      bigint
);

ALTER TABLE "user"
    ADD FOREIGN KEY ("role") REFERENCES "userRole" ("role");

ALTER TABLE "user"
    ADD FOREIGN KEY ("email") REFERENCES "actor" ("email");

ALTER TABLE "publication"
    ADD FOREIGN KEY ("email") REFERENCES "actor" ("email");

ALTER TABLE "publication"
    ADD FOREIGN KEY ("state") REFERENCES "publicationState" ("state");

ALTER TABLE "response"
    ADD FOREIGN KEY ("email") REFERENCES "actor" ("email");

ALTER TABLE "response"
    ADD FOREIGN KEY ("publicationId") REFERENCES "publication" ("id");

ALTER TABLE "extra"
    ADD FOREIGN KEY ("publicationId") REFERENCES "publication" ("id");

ALTER TABLE "extra"
    ADD FOREIGN KEY ("type") REFERENCES "extraType" ("type");

ALTER TABLE "payment"
    ADD FOREIGN KEY ("extraId") REFERENCES "extra" ("id");

insert into "publicationState" (state,description) values ('CONFIRM_WAIT','waiting user to confirm email');
insert into "publicationState" (state,description) values ('CONFIRMED','after successful email confirmation');
insert into "publicationState" (state,description) values ('POSTED','free publication has been posted');
insert into "publicationState" (state,description) values ('PAY_WAIT','waiting for extra payment');
insert into "publicationState" (state,description) values ('PAYED','extra payment succeed');
insert into "publicationState" (state,description) values ('REJECTED','after unsuccessful payment attempt');
insert into "publicationState" (state,description) values ('POSTED_EXTRA','paid publication has been posted');
insert into "publicationState" (state,description) values ('GOT_RESPONSE','there were some responses');
insert into "publicationState" (state,description) values ('ABORTED','was aborted by user');
insert into "publicationState" (state,description) values ('SUCCESS','successfully found');
insert into "publicationState" (state,description) values ('FINISHED','end of extra publication');

insert into "userRole"(role,description,permissions)values('ADMIN','administrator role',-1);
insert into "userRole"(role,description,permissions)values('UNAUTHORISED','unknown',0);
insert into "userRole"(role,description,permissions)values('OWNER','the one who lost his pet',1);
insert into "userRole"(role,description,permissions)values('VOLUNTEER','the one who found someone pet',2);
insert into "userRole"(role,description,permissions)values('RESPONDER','the one who made response',3);

insert into "extraType" (type,description,price)values('LOST','owner looks for his pet',120);
insert into "extraType" (type,description,price)values('FOUND','volunteer looks for pet owner',100);


INSERT INTO "actor" (email,role,token) VALUES ('Duis@ipsumnuncid.ca','ADMIN','1E58DF97-AF41-8E61-12C7-FC4B96943459'),('Cras@ullamcorper.org','RESPONDER','B6ED883B-A5FB-1825-2CBD-F4A28AC99DE7'),('sem.Pellentesque.ut@justonecante.com','RESPONDER','A9554B81-CDBA-F7F7-8B9B-99D6E806070C'),('Fusce@amet.ca','OWNER','7A6DFD11-3A3D-FC34-D55E-F5C455D31F77'),('dui.nec.urna@elementumat.edu','RESPONDER','0F5537C1-6578-F3BF-8210-2C220F215935'),('gravida@Incondimentum.org','OWNER','08E80137-5250-82B1-5D78-A52580A0EF51'),('elit@tellusAeneanegestas.ca','ADMIN','31F73085-445A-2B05-F8F0-77685DB2D149'),('amet.orci@tinciduntdui.net','ADMIN','0E638CA6-21EF-898D-1B51-5A6830C65490'),('interdum.Sed@fermentumconvallis.net','VOLUNTEER','E8A41566-23CC-9B50-C46A-5ACF6A4337B4'),('sed.leo.Cras@diam.ca','RESPONDER','C09F7017-772E-1B67-1D6C-413F2E3EDEA5'),('sed@gravida.co.uk','ADMIN','D0766109-1569-C691-E765-30523742D31A'),('Suspendisse@ridiculus.org','OWNER','AC230E04-429C-B509-BA34-E325331EB1AB'),('tincidunt@sit.net','VOLUNTEER','D6464CBE-4DD0-EB30-6238-7E2CC2C74C84'),('habitant@Quisqueaclibero.org','RESPONDER','A41EB00F-D99A-2647-8B3C-D0C0392A3480'),('nascetur@lacusEtiam.org','ADMIN','CF217985-66F8-DED7-55C5-39EE2F77D9C3'),('Aenean@Maurisnulla.org','RESPONDER','AE5367E2-CDFD-11F3-44B0-DE84F0C9DE7A'),('volutpat@lacus.net','VOLUNTEER','C2A43656-B199-D35B-4A50-370679AD4B75'),('placerat.orci.lacus@diam.org','ADMIN','9A90CD37-B8F1-B53D-CC97-78FCF7B7AC04'),('mi@eu.com','ADMIN','F5416F94-7D8A-528B-148E-5CCE07B04695'),('Phasellus@liberoduinec.com','ADMIN','9B5AEAF7-99DB-8984-9BA5-A11B3A513E58'),('ornare@orci.org','RESPONDER','F7BFC326-F876-2451-0303-1DF1CE0F294F'),('pede.nec.ante@nuncinterdum.edu','VOLUNTEER','145D7637-B979-1E9A-8D6A-BF39E1EB82DF'),('ipsum.Phasellus@urnaNullam.ca','VOLUNTEER','E7C2833F-F727-F5FB-E505-E3C477C81599'),('lorem.lorem@ornareInfaucibus.org','ADMIN','703FFBFB-9AEB-3A82-9A2C-ECDC989C5062'),('a@non.co.uk','RESPONDER','039A854B-6097-62E6-19D1-2C51D3DF0500'),('Sed.dictum@netusetmalesuada.net','OWNER','91CA10A7-4021-3927-A288-5A96F7831DD2'),('sem@nonegestas.org','ADMIN','5F11BC13-23C0-1403-35AA-38F4EC6C2381'),('magnis@magnaDuisdignissim.co.uk','OWNER','394DCC04-83A4-F508-802A-86497CAE0D55'),('eu.lacus.Quisque@sitamet.org','VOLUNTEER','AA23B37C-4F8F-E0B8-6C1B-6E1674E12616'),('tellus@ultrices.co.uk','VOLUNTEER','F6F22AA6-5B12-5A5B-6E21-BD3BE95E8573'),('iaculis@antedictumcursus.edu','VOLUNTEER','B6727D17-95F6-9C94-5BA8-1F2CF696E1B7'),('ut@liberoest.net','VOLUNTEER','0AE03A08-7642-7AE2-9F69-73B2DDA67DD0'),('pellentesque.a@Vestibulumaccumsan.edu','ADMIN','69B11BFF-69BF-7D25-E2B6-165586A1F0A0'),('nibh@musAeneaneget.com','RESPONDER','FAF83DB7-1E87-6F80-72CC-FA97FD31B732'),('mauris.eu.elit@nisia.edu','RESPONDER','02772C0E-FDB8-834C-664A-E15E8519F970'),('faucibus.lectus.a@dictumeleifendnunc.edu','RESPONDER','79C9B2C4-3E6B-D94C-54F9-42FD99F34A05'),('natoque.penatibus.et@Vivamusmolestie.net','OWNER','E12FD965-B577-187D-4D02-E4BA903B59C2'),('Ut@pellentesqueafacilisis.com','ADMIN','9A5FFC55-3768-6565-990A-2E2CF6092C0E'),('in@aenim.org','OWNER','4F010CDA-4E06-254B-336F-079318F250EB'),('lacus.Mauris.non@Loremipsumdolor.com','ADMIN','1C876342-E526-0B42-4FD6-202C1CC9CA83'),('enim.nec.tempus@magnisdis.ca','VOLUNTEER','F8B3E79F-E519-537E-D06F-5B25578C46FE'),('Class.aptent.taciti@Nullamscelerisque.edu','VOLUNTEER','97AA42C2-48B0-4C98-AD96-916554BFA078'),('dui.augue.eu@eu.org','ADMIN','D3F89D98-0E0B-E065-FA21-99FEFD285E1F'),('et@Nullatincidunt.edu','ADMIN','FAF6E6C5-BB0F-E224-CF1F-5D5F3691C647'),('vitae.nibh.Donec@vitae.org','ADMIN','E0421909-F1FC-0BEA-DC64-ACADC75E9495'),('adipiscing@Aeneanegestashendrerit.edu','OWNER','81376AF3-82A0-13AA-7BD3-4B5735188FC3'),('Cum@Aliquamvulputate.ca','VOLUNTEER','E2508140-A54B-9818-732D-8ABEA0C52BC4'),('Donec.felis.orci@orciin.org','VOLUNTEER','16331DCA-A6CB-7675-3D82-78262DF99FDF'),('risus.varius@sitamet.org','RESPONDER','1199B831-73C2-582A-B8CD-1BF3EBBC2AAE'),('a@Sedeu.com','VOLUNTEER','B5B132B2-706D-02E9-73EE-05998941504C'),('orci@Aliquamrutrumlorem.ca','ADMIN','54A12456-FA0B-A369-BD7E-C3FE5B8A2BC9'),('Nunc.pulvinar.arcu@lectusquismassa.org','OWNER','D8C25670-C45F-1624-EE37-6C71200EC8C7'),('sit.amet.risus@tellusPhaselluselit.org','VOLUNTEER','44EC144A-B4DE-2004-FEAB-9BD6FC96697E'),('non@litoratorquentper.net','VOLUNTEER','D17B135E-DEF4-1D43-BE9E-0231BA2BE845'),('erat.semper@nisiAenean.org','OWNER','FF67479C-A2AD-A8EE-CC65-EEA8B6AD1798'),('accumsan@placerat.co.uk','VOLUNTEER','E671B24E-4F63-027A-79AF-F0FE9DFFF441'),('nec.tellus@sem.com','OWNER','C8BF7903-6240-D909-379A-76B4611F72F6'),('a.scelerisque@quis.co.uk','RESPONDER','77ABFA44-7361-912D-74AA-B06B689EC8BD'),('ante.lectus.convallis@sitamet.edu','RESPONDER','00781F31-0CFD-2E6F-4ADD-E944ED70EE8A'),('massa@risus.net','OWNER','1957E4DA-0D0B-FBB3-8510-5B6C6B578DBB'),('nibh.Aliquam@massaInteger.co.uk','VOLUNTEER','B284316A-52B7-778B-73FA-279829468F45'),('ante.Nunc.mauris@In.co.uk','OWNER','638DFEFC-8D0F-1BBE-F437-6100305AFF5D'),('arcu@porttitor.org','ADMIN','DB5BF91C-5FDC-9A04-8833-A390D5FEBDFE'),('ullamcorper.Duis@liberoInteger.org','OWNER','2839D3B4-4E2F-5EBF-F772-3E7275AF2891'),('tellus@sitamet.edu','RESPONDER','BBFF49D9-1719-B50D-14EF-9118A8439E1E'),('dis@dolorNulla.org','ADMIN','C0A997D8-E112-3F80-128C-4044AB4851CF'),('nunc@acrisusMorbi.com','OWNER','6F9B90F9-005D-CDB5-A3E2-703216AFEE25'),('Phasellus.elit.pede@magnisdis.com','RESPONDER','00E0C751-8B2A-DB17-F309-79047EE30332'),('semper@Integerinmagna.ca','VOLUNTEER','20452C0C-11AA-DCFA-34FA-1E98BAEEB8D7'),('arcu.Nunc@Crasegetnisi.ca','RESPONDER','C57B8786-39DB-23A8-728A-68370A920661'),('enim@risus.com','OWNER','9D60EE10-6231-CFD7-23A6-7AE5E40FD35C'),('quis.pede.Suspendisse@ullamcorper.edu','VOLUNTEER','3A40DF72-627B-B204-DC9D-2114D637E2C9'),('aliquet@Nam.ca','OWNER','46F7B7B8-CD69-F338-CFC2-BC6D3E82E5F8'),('consectetuer.mauris@Fusce.net','RESPONDER','A1E596F1-760F-00E2-63F3-B06B4FFB24B6'),('tempus.lorem@quamelementum.co.uk','VOLUNTEER','A8FAEAA0-6C69-D404-81C4-5B0A4779C92E'),('euismod.in@Donec.ca','ADMIN','541EA377-DDE0-6CA8-8E26-79D4344E41A6'),('Proin.mi@vestibulumnec.com','RESPONDER','8274021B-CCA8-C296-5907-2B8063A3BC34'),('ridiculus.mus.Donec@dictum.ca','VOLUNTEER','EA54218D-0923-E83B-2978-32EAAD48E365'),('at@Loremipsum.ca','OWNER','094191AE-72CC-0E29-231B-DE8E5AFE0244'),('fringilla.porttitor.vulputate@semper.co.uk','ADMIN','2FC6648F-1DDD-C27E-D2B2-3A8F6660D3D2'),('non.egestas.a@In.org','ADMIN','C8DA31C4-1321-4170-4108-38F79E60DB72'),('orci@porttitoreros.com','VOLUNTEER','7C6E23F9-484C-DBA6-3C91-F5B48A778326'),('mi.ac.mattis@ipsum.co.uk','ADMIN','D75C43B6-3575-8F88-FDC9-74916E6FC94A'),('Nunc.ullamcorper@sagittis.co.uk','RESPONDER','4AC675B5-3DD5-D49D-966D-2B5EC6B3CFF0'),('Proin.eget@odio.org','OWNER','ED1B05B1-918A-E034-C0E4-7E52869EA54F'),('Duis@et.org','ADMIN','F0CC735D-E6FF-DDAE-1224-2D9369B87CE8'),('malesuada.malesuada@sit.edu','RESPONDER','0469DF84-6D03-13F0-9EA9-589C45577D1C'),('blandit.Nam.nulla@non.net','RESPONDER','E8500C2D-8D2D-0F68-3ABF-E3F216F7CA85'),('aliquam.iaculis.lacus@Aliquameratvolutpat.org','ADMIN','7D1F9552-0EAF-8515-BDC8-65FAFCF87149'),('fringilla.est.Mauris@Donecat.edu','ADMIN','216E37B2-3C3E-FE82-032A-7B865482A4E1'),('luctus@nuncrisus.com','RESPONDER','A2AFE1B1-FA09-61BC-75BD-A6BE6DC60721'),('id.magna.et@ipsum.com','ADMIN','827116D7-C42D-833E-9B3B-BB61375129F5'),('Etiam.imperdiet@suscipitnonummyFusce.ca','OWNER','F33B203D-66A7-8909-AFB7-76E4ADEE5BF4'),('non.vestibulum@aliquam.edu','VOLUNTEER','C000E976-E635-9BF1-B66B-7FB59513F5A7'),('magna.Suspendisse@DonecnibhQuisque.org','ADMIN','A082A98D-65AD-178F-707A-A350A12C1273'),('Mauris@sitametorci.org','VOLUNTEER','69BEF8D5-4B74-DDC2-BA9C-9820C4DD97E5'),('nisi@posuereenim.org','RESPONDER','ABE8D6A6-BD55-A403-5A40-87BDDDFB953E'),('imperdiet.ullamcorper@lectusNullam.ca','OWNER','46CE989C-DD1B-9FB2-D5F1-CC6442FB44E3'),('mattis.semper.dui@ametmetus.net','ADMIN','C4E01506-E37A-B94A-38BE-4B0BC9D06547'),('eros.nec@nec.co.uk','RESPONDER','0465A66A-8AB5-02BA-F5B4-47E2CD491F76');


INSERT INTO "user" (email,role,token,name,phone,"isBlocked","isActual",password) VALUES
('tempus.lorem@quamelementum.co.uk','OWNER','0A1EFB8A-4B6F-2394-6C2A-1C17F516B29D','Callum','(743) 844-9616','True','False','CF85208B-01F7-20DA-5183-DC6DE611D598'),
('malesuada.malesuada@sit.edu','OWNER','BE4D93C1-65E2-250E-906A-DC3778AE6A69','Hiram','(653) 227-0321','False','False','98A7B51C-69AC-C68B-FA20-6FF8DC515E80'),
('Etiam.imperdiet@suscipitnonummyFusce.ca','OWNER','91D6FF09-5F5B-65F4-D485-591CF80ACFD3','Marny','(837) 470-7213','False','False','402B7345-8C08-3448-8BAB-3BD785E0CB2B'),
('a@non.co.uk','ADMIN','08E6F825-EAD6-7892-9B0E-7F3B474C0252','Melodie','(507) 803-3720','True','False','7A25CD4F-FD87-A763-B435-91F7A986A9E7'),
('orci@porttitoreros.com','VOLUNTEER','2BC6EE4A-BAE3-8AB6-D719-4102EE924BED','Kaseem','(236) 315-3161','False','False','EC4CEAED-F672-7480-F811-06C02EC914C9'),
('blandit.Nam.nulla@non.net','RESPONDER','3D392436-86D2-68E9-C920-4CCDA1645EBE','Ezra','(204) 373-3325','True','True','60EE5C1B-4547-EA94-BC7B-D0D366422622'),
('Duis@et.org','RESPONDER','0581F4EC-4F5F-D089-B63E-CCFEFE3EC649','Xander','(747) 399-9292','False','False','3EF3D604-2841-EF45-0B87-5E7FC827E264'),('Proin.mi@vestibulumnec.com','OWNER','224C331B-F361-758F-93B6-DF794E27B07E','Benedict','(713) 945-4378','False','False','39322AF9-3A35-CB57-CE30-15782C87227B'),('id.magna.et@ipsum.com','VOLUNTEER','BB65D451-413F-9ED3-0CF6-8A432F433E9E','Juliet','(218) 948-4608','True','False','07E91462-20A9-7A3C-8F52-01C90C9CA31A'),('a@Sedeu.com','VOLUNTEER','209243B3-AF75-1F73-07C2-E45133C811B2','Vladimir','(556) 669-0551','True','True','393F387E-2274-E407-2C35-75BA611691D3'),
('luctus@nuncrisus.com','OWNER','E273BA1C-5CC5-6D69-5FC1-69776B77F1C4','Forrest','(411) 323-7015','True','True','3D276CD1-577D-C093-BCA6-22CF045A29B9'),
('massa@risus.net','ADMIN','CBB65EE4-EA28-389C-4368-DE082ECBAEEE','Violet','(989) 324-6911','False','False','7F614302-48AE-DB63-C0FB-8DB5E5946336'),
('arcu@porttitor.org','ADMIN','C5EC2657-23E7-C44E-EC64-6087215E24F7','Harding','(155) 748-9589','False','False','E88491D0-3392-1C07-05FD-C4D7EFEF5D3F'),
('mi@eu.com','ADMIN','A82E477F-D8A6-A1D5-1DEA-84FDC28B7E45','Ria','(720) 771-9216','True','True','B01FDB58-D88A-C0E8-D660-B28B50C43AF6'),
('Cum@Aliquamvulputate.ca','ADMIN','5C2F683D-D96C-FCD8-C11C-1254F7AC766C','Giselle','(872) 521-0031','False','False','9476A185-C601-9E40-BDD3-0535BCBDA4D0')




