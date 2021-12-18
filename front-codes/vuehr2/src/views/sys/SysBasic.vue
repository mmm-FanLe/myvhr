<template>
  <el-tabs v-model="activeName" type="card">
    <el-tab-pane label="部门管理" name="DepMana">
      <DepMana></DepMana>
    </el-tab-pane>
    <el-tab-pane label="职位管理" name="PosMana">
      <PosMana></PosMana>
    </el-tab-pane>
    <el-tab-pane label="职称管理" name="JobLevelMana">
      <JobLevelMana></JobLevelMana>
    </el-tab-pane>
    <el-tab-pane label="权限组" name="PermissMana">
      <PermissMana></PermissMana>
    </el-tab-pane>
    <el-tab-pane label="奖惩管理" name="EcMana">
      <EcMana></EcMana>
    </el-tab-pane>
  </el-tabs>
</template>

<script>

import DepMana from "../../components/basic/DepMana";
import EcMana from "../../components/basic/EcMana";
import JobLevelMana from "../../components/basic/JobLevelMana";
import PermissMana from "../../components/basic/PermissMana";
import PosMana from "../../components/basic/PosMana";

export default {
  name: "SysBasic",
  data(){
    return {
      activeName:'DepMana',

    };
  },
  components:{
    DepMana,
    EcMana,
    JobLevelMana,
    PermissMana,
    PosMana
  }
}
</script>

<style scoped>

</style>