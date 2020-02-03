'use strict';
module.exports = (sequelize, DataTypes) => {
  const users = sequelize.define('users', {
    user_num: 
    {
      type:DataTypes.INTEGER,
      primaryKey:true,
      allowNull:false,
      autoIncrement: true

    },
    user_uid:{

      type:DataTypes.STRING,
      allowNull:false,
    },
    user_id: {
      type:DataTypes.STRING(50),
      allowNull:false,
      unique:true,
 

    },
    user_name:{
      type:DataTypes.STRING(100),
      allowNull:false
    },
    user_nickname:{
      type:DataTypes.STRING(50),
      allowNull:false,
      unique:true
    },
    user_password: {
     type:DataTypes.STRING(100),
     allowNull:false
    },
    user_email: {
     type: DataTypes.STRING(100),
     allowNull:false
    },
   
    user_phone: {
      type: DataTypes.STRING(100),
      allowNull:false
     },
   
    
  }, {
      tableName:"users"
  });
  users.associate = function(models) {
    // associations can be defined here
  };
  return users;
};