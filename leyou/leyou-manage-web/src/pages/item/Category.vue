<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
import {treeData} from "../../mockDB";
import message from "../../components/messages"

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
        treeData
      }
    },
    methods: {
      handleAdd(node) {
        this.$http.post("/item/category/addCategory",node).
        then(function(resp){
          message.success("新增成功")
        }).catch(function(error){
          message.error("新增失败"+error)
        });
      },
      handleEdit(id, name) {
        
        var category = {
          id:id,
          name:name
        };

        this.$http.post("/item/category/modifyCategory",category).
        then(function(resp){
          message.success("修改成功")
        }).catch(function(error){
          message.error("修改失败"+error)
        });

        // console.log("edit... id: " + id + ", name: " + name)
      },
      handleDelete(id) {
        this.$http.post("/item/category/deleteCategory?id="+id).then(function(resp){
          message.success("删除成功")
        }).catch(function(error){
          message.error("删除失败"+error)
        });
        console.log("delete ... " + id)
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
