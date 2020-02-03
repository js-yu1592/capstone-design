'use strict';
module.exports = (sequelize, DataTypes) => {
  const board = sequelize.define('board', {
    board_num: {
        type:DataTypes.INTEGER,
        primaryKey:true,
        alloNull:false,
        autoIncrement: true
    },
    board_nickname:{
      type:DataTypes.STRING(20),
      alloNull:false
    },
    board_content:{
      type:DataTypes.STRING(255),
      alloNull:false
    },
    board_hit:{
      type:DataTypes.STRING(10),
      alloNull:false
    },
  
  }, {

    tableName:"board"
  });
  board.associate = function(models) {
    // associations can be defined here
  };
  return board;
};