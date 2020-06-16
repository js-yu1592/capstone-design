'use strict';
module.exports = (sequelize, DataTypes) => {
  const comment = sequelize.define('comment', {
     cmt_num: {
       type:DataTypes.INTEGER,
       primaryKey:true,
       allowNull:false,
       autoIncrement: true
      },
 cmt_uid:{
  type:DataTypes.STRING,
  allowNull:false,
 },
cmt_context:{
  type:DataTypes.STRING(255),
  allowNull:false,
  
},

cmt_nickname:{
 type:DataTypes.STRING(100),
 allowNull:false
}

    
  
  }, { 
    
    tableName:"comment"
  
  });
  comment.associate = function(models) {
    // associations can be defined here
  };
  return comment;
};