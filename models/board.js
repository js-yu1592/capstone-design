'use strict';
module.exports = (sequelize, DataTypes) => {
  const board = sequelize.define('board', {
    board_num: {
        type:DataTypes.INTEGER,
        primaryKey:true,
        allowNull:false,
        autoIncrement: true
    },
    board_uid:{
      type:DataTypes.STRING,
      allowNull:false,
    },   
     board_title:{
      type:DataTypes.STRING(100),
      allowNull:false
    },   
 
    board_content:{
      type:DataTypes.STRING(255),
      allowNull:false
    },
    
    board_nickname:{
      type:DataTypes.STRING(20),
      allowNull:false
    },
    board_email:{
      type:DataTypes.STRING(100),
      allowNull:false
    },  

  
  }, {

    tableName:"board"
  });
  board.associate = function(models) {
    // board.belongsTo(models.users, {foreignKey:'board_num', targetKey: 'id'})
  };
  return board;
};