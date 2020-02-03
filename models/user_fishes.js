'use strict';
module.exports = (sequelize, DataTypes) => {
  const user_fishes = sequelize.define('user_fishes', {
    fish_num:{
       type: DataTypes.INTEGER,
       primaryKey:true,
       allowNull:false,
       autoIncrement: true
    },
    fish_name: {
       type:DataTypes.STRING(50),
       allowNull:false
    },
    fish_length: {
       type:DataTypes.DECIMAL(5,2),
       allowNull:true
    },
   
    fish_weight:{
      type:DataTypes.DECIMAL(5,2),
      allowNull:true
    } ,
    fish_comment: {
      type:DataTypes.STRING(255),
      allowNull:true
    },
    fish_lat:{
      type:DataTypes.DECIMAL(16,14),
       allowNull:false
    },
    fish_lot:{
      type:DataTypes.DECIMAL(17,14),
      allowNull:false
    }
  }, {});
  user_fishes.associate = function(models) {
    // associations can be defined here
  };
  return user_fishes;
};