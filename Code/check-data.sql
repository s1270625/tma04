select o.qrCode, o.createDtm, o.status, b.branchName, i.itemName, d.qty 
from orders o, orderdetails d, branches b, items i 
where o.oId = d.oId 
and o.bId = b.bId 
and d.itemId = i.itemId 
and o.qrCode = 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454';