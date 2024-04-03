insert into branches (BRANCHCODE, BRANCHNAME, TOTALSEATS) values ('KLB', 'Kowloon Bay', 10);
insert into branches (BRANCHCODE, BRANCHNAME, TOTALSEATS) values ('TWS', 'Tsz Wan Shan', 8);
insert into ItemCategories (icCode, icName) values ('DRINK', 'Drink');
insert into ItemCategories (icCode, icName) values ('NOD', 'Noodle');
insert into Items (itemCode, itemName, icId) 
select 'WTN', 'Wonton Noodle', icId 
from ItemCategories where icCode = 'NOD';
insert into Items (itemCode, itemName, icId)
select 'COKE', 'Coke', icId
from ItemCategories where icCode = 'DRINK';
insert into orders (qrCode, createDtm, status, bId) 
select
    'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 
    now(), 
    'A',
    bId
from branches
where BRANCHCODE = 'KLB';