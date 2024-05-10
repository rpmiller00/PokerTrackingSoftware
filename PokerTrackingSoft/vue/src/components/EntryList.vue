<template>

    <h1>Apply a Filter to View Entries</h1>

    <div class="dropdown">
        <div class="dropdown-content">
            <h5>Game Type: </h5>
            <select id="game-type" name="game-type" v-model="selectedGame">
                <option value="">All</option>
                <option value="NLH">NLH</option>
                <option value="PLO">PLO</option>
                <!-- <option value="Deleted">Deleted</option> -->
            </select>

            <button v-on:click="getFilteredList()"><strong>Apply Filter</strong></button>

        </div>
        <div class="dropdown-content">
            <h5>Game Size: </h5>
            <select id="game-size" name="game-size" v-model="selectedSize">
                <option value="">All</option>
                <option value="1-3">1-3</option>
                <option value="2-5">2-5</option>
                <option value="Other">Other</option>
                <!-- <option value="Deleted">Deleted</option> -->
            </select>

            <button v-on:click="getFilteredList()"><strong>Apply Filter</strong></button>

        </div>
        <div class="dropdown-content">
            <h5>Location: </h5>
            <select id="game-location" name="game-location" v-model="selectedVenue">
                <option value="">All</option>
                <option value="Delaware Park">Delaware Park</option>
                <option value="Other">Other</option>
                <!-- <option value="Deleted">Deleted</option> -->
            </select>

            <button v-on:click="getFilteredList()"><strong>Apply Filter</strong></button>

        </div>

    </div>
    <h1>Statistics</h1>
    <div class="statistics">
        <div class="winnings">
            <p>Net Winnings: ${{ netWinnings }}</p>
        </div>
        <div class="sessions">
            <p># of Sessions: {{ totalSessions }}</p>
        </div>
        <div class="netwinnings">
            <p v-show="totalSessions !== 0">Net Winnings per Session ${{ Math.round(netWinnings / totalSessions) }}</p>
        </div>
        <div class="netwinningsperhour">
            <p v-show="totalSessions !== 0"> Net Winnings per Hour $ {{ Math.round(netWinnings / totalHours) }}</p>
        </div>
    </div>
    <div class="entry-container">
        <DisplaySession v-bind:key="entry.id" v-for="entry in filteredList" v-bind:entry="entry" />
    </div>
</template>

<script>
import DisplaySession from '../components/DisplaySession.vue'

export default {
    data() {
        return {
            selectedGame: "",
            selectedSize: "",
            selectedVenue: "",
            filteredList: this.$store.state.entryList,
            netWinnings: 0,
            totalSessions: 0,
            totalHours: 0
        }
    },
    computed: {
        entryList() {
            return this.$store.state.entryList
        }
    },
    components: {
        DisplaySession
    },
    created() {
        this.$store.commit("UPDATE_ENTRIES");
    },
    updated() {
        this.getNetWinnings();
        this.getNumSessions();
        this.getNumHours();
    },
    methods: {

        // getFilteredListByType() {
        //     this.filteredList = this.$store.state.entryList.filter((entry) => { return entry.gameType.includes(this.selectedGame) });

        // },

        // getFilteredListBySize() {
        //     this.filteredList = this.$store.state.entryList.filter((entry) => { return entry.gameSize.includes(this.selectedSize) });

        // },
        // getFilteredListByVenue() {
        //     this.filteredList = this.$store.state.entryList.filter((entry) => { return entry.location.includes(this.selectedVenue) });

        // },
        getFilteredList() {
            this.filteredList = this.$store.state.entryList.filter((entry) => { return entry.gameType.includes(this.selectedGame) && entry.gameSize.includes(this.selectedSize) && entry.location.includes(this.selectedVenue) });
        },

        getNetWinnings() {
            this.netWinnings = this.filteredList.reduce((acc, curr) => {
                return acc + curr.amount;
            }, 0);
        },
        getNumSessions() {
            this.totalSessions = this.filteredList.reduce((acc) => {
                return acc + 1;
            }, 0);
        },
        getNumHours(){
            this.totalHours = this.filteredList.reduce((acc, curr) => {
                return acc + curr.hours;
            }, 0);
        },


    }
};

</script>

<style>
.dropdown {
    display: flex;
    justify-content: space-evenly;
}

h1 {
    text-align: center;
}

.statistics {
    text-align: center;
    font-weight: bold;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}
</style>