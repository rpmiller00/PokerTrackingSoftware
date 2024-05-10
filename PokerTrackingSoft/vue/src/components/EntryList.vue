<template>

    <body>
        <div class="navigation">
            <NavOther />
        </div>
        <div class="dropdown">
            <h1>Apply a Filter to View Entries</h1>
            <div class="filter-text">
                <h5>Game Type: </h5>
                <select id="game-type" name="game-type" v-model="selectedGame">
                    <option value="">All</option>
                    <option value="NLH">NLH</option>
                    <option value="PLO">PLO</option>
                    <!-- <option value="Deleted">Deleted</option> -->
                </select>

                <button v-on:click="getFilteredList()"><strong>Apply Filter</strong></button>
            </div>
            <div class="filter-text">
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
            <div class="filter-text">
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
        <div class="statistics">
            <h1>Statistics</h1>
            <div class="stats-text">
                <div class="winnings">
                    <p>Net Winnings: ${{ netWinnings }}</p>
                </div>
                <div class="sessions">
                    <p># of Sessions: {{ totalSessions }}</p>
                </div>
                <div class="netwinnings">
                    <p v-show="totalSessions !== 0">Net Winnings per Session ${{ Math.round(netWinnings / totalSessions)
                        }}
                    </p>
                </div>
                <div class="netwinningsperhour">
                    <p v-show="totalSessions !== 0"> Net Winnings per Hour $ {{ Math.round(netWinnings / totalHours) }}
                    </p>
                </div>
            </div>
        </div>
        <div class="entry-container">
            <DisplaySession class="sessions" v-bind:key="entry.id" v-for="entry in filteredList" v-bind:entry="entry" />
        </div>
    </body>
</template>

<script>
import DisplaySession from '../components/DisplaySession.vue'
import NavOther from '../components/NavOther.vue'
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
        DisplaySession,
        NavOther
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
        getNumHours() {
            this.totalHours = this.filteredList.reduce((acc, curr) => {
                return acc + curr.hours;
            }, 0);
        },


    }
};

</script>

<style>
body {
    display: grid;
    margin: 0;
    padding: 0;
    width: 100vw;
    height: 100vh;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    grid-template-areas:
        "nav filter filter stats stats"
        "sessions sessions sessions sessions sessions";
}

h1 {
    text-align: center;
}

.navigation {
    grid-area: nav;
    position: absolute;
    top: 0;
    left: 0;
}

.dropdown {
    grid-area: filter;
}

.filter-text {
    text-align: center;
}

.statistics {
    font-weight: bold;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    grid-area: stats;
}
.stats-text{
    text-align: center;

}

.entry-container {
    grid-area: sessions;
}
</style>