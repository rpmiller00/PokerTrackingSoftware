<template>
    <div>
        <EntryList/>
    </div>
</template>

<script>
import EntryList from '../components/EntryList.vue'
export default {
    components: {
        EntryList,
    },
  created() {
        this.$store.commit("UPDATE_ENTRIES");
    },

}
</script>

<style>
.spacer {
  height: 15rem;
}

</style>
