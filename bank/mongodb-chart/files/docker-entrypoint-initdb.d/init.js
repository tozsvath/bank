use bank;
db.createUser({user: "bank", pwd: "bank123", roles:[{role:"readWrite", db: "bank"}]});
use bank;
db.balance.insert({"user": "testUser1","amount":100,"_class" : "mentoring.epam.bank.domain.Balance"});
db.balance.insert({"user": "testUser2","amount":111,"_class" : "mentoring.epam.bank.domain.Balance"});
db.balance.insert({"user": "testUser3","amount":222,"_class" : "mentoring.epam.bank.domain.Balance"});
db.balance.insert({"user": "testUser4","amount":333,"_class" : "mentoring.epam.bank.domain.Balance"});
db.balance.insert({"user": "testUser5","amount":444,"_class" : "mentoring.epam.bank.domain.Balance"});