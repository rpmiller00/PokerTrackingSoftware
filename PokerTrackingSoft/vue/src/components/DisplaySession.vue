<template>
    <div class="entry-card">
        <p>Entry #: {{ entry.entryId }}</p>
        <p>Win/Loss: ${{ entry.amount }}</p>
        <p>Stake: {{ entry.gameSize }}</p>
        <p>Game: {{ entry.gameType }}</p>
        <p>Location: {{ entry.location }}</p>
        <p>Duration: {{ entry.hours }} hours</p>
        <button v-on:click="deleteEntry()">Delete</button>
    </div>

</template>

<script>
import EntryService from '../services/EntryService.js';
export default {
    data() {
        return {
            hours: this.calculateDifference()
        }
    },
    props: {
        entry: {
            type: Object,
            required: true,
        },
    },
    methods: {

        deleteEntry() {
            EntryService.deleteEntry(this.entry.entryId);
            this.$router.go(0);
        },
        calculateDifference() {
            var startDateTime = new Date(this.entry.startTime);
            var endDateTime = new Date(this.entry.endTime);

            // Calculate the difference in milliseconds
            var difference = Math.abs(endDateTime - startDateTime);

            // Convert milliseconds to hours
            var hours = difference / (1000 * 60 * 60);

            return hours;

        }

    }
}

</script>

<style>
.entry-card {
    border: 2px solid black;
    margin: 10px;
    padding: 10px;
    background-color: rgb(177, 177, 177);
    font-weight: bold;
}
</style>