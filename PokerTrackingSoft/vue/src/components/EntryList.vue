<template>
    <div class="filter">
                <h3>Search By Game </h3>
                <div class="dropdown">
                    <div class="dropdown-content">
                        <select id="game-type" name="game-type" v-model="selectedOption">
                            <option value="">All</option>
                            <option value="NLH">NLH</option>
                            <option value="PLO">PLO</option>
                            <!-- <option value="Deleted">Deleted</option> -->
                        </select>

                        <button v-on:click="getFilteredList()"><strong>Apply Filter</strong></button>

                    </div>
                </div>
            </div>
    <div class="entry-container">
        <DisplaySession v-bind:key="entry.id" v-for="entry in filteredList" v-bind:entry="entry"/>
    </div>
</template>

<script>
import DisplaySession from '../components/DisplaySession.vue'

export default {
  data() {
        return {
            selectedOption: "",
            filteredList: this.$store.state.entryList,
        }
    },
    computed: {
        entryList(){
            return this.$store.state.entryList
        }
    },
    components: {
        DisplaySession
    },
  created() {
        this.$store.commit("UPDATE_ENTRIES");
    },
    methods:{

        getFilteredList() {
            this.filteredList = this.$store.state.entryList.filter((entry) => { return entry.gameType.includes(this.selectedOption) });

        },

    }
};

</script>

<style>

</style>