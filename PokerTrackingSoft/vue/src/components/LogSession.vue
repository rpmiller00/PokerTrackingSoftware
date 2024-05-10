<template>
    <div class="form-container">
        <form v-on:submit.prevent="submitForm">
            <div class="form-data">
                <label for="amount">Amount Won/Lost: $ </label>
                <input type="text" id="amount" v-model="updatedEntry.amount">
            </div>
            <div class="form-data">
                <label for="game-size">Game Size (blinds): </label>
                <!-- <input type="text" id="game-size" v-model="updatedEntry.gameSize"> -->
                <select id="game-size-dropdown" v-model="updatedEntry.gameSize">
                    <option value="1-3">1-3</option>
                    <option value="2-5">2-5</option>
                    <option value="Other">Other</option>
                </select>
            </div>
            <div class="form-data">
                <label for="game-type">Game Type: </label>
                <!-- <input type="text" id="game-type" v-model="updatedEntry.gameType"> -->
                <select id="game-type-dropdown" v-model="updatedEntry.gameType">
                    <option value="NLH">NLH</option>
                    <option value="PLO">PLO</option>
                </select>
            </div>
            <div class="form-data">
                <label for="location">Location: </label>
                <!-- <input type="text" id="location" v-model="updatedEntry.location"> -->
                <select id="game-location-dropdown" v-model="updatedEntry.location">
                    <option value="Delaware Park">Delaware Park</option>
                    <option value="Other">Other</option>
                </select>
            </div>
            <div class="form-data">
                <label for="start-time">Start Time: </label>
                <input type="datetime-local" id="start-time" v-model="updatedEntry.startTime">
            </div>
            <div class="form-data">
                <label for="end-time">Start Time: </label>
                <input type="datetime-local" id="end-time" v-model="updatedEntry.endTime">
            </div>
            <div class="form-data">
                <input type="submit">
            </div>
        </form>
    </div>
</template>

<script>
import EntryService from '../services/EntryService';
export default {
    data() {
        return {
            entry: this.$store.state.entry
        }
    },
    computed: {
        updatedEntry() {
            return {
                userId: this.$store.state.user.id,
                amount: this.entry.amount,
                gameSize: this.entry.gameSize,
                gameType: this.entry.gameType,
                location: this.entry.location,
                startTime: this.entry.startTime,
                endTime: this.entry.endTime,
                hours: this.entry.hours
            }
        },
    },
    methods: {
        submitForm() {
            this.updatedEntry.hours = this.calculateDifference();
            EntryService
                .addEntry(this.updatedEntry)
                .then(response => {

                    if (response.status === 201) {
                        //this.$store.commit();
                        alert("Entry Logged Successfully")
                        this.$store.commit("UPDATE_ENTRIES");
                        this.$router.go();
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, 'updating');
                });
        },
        handleErrorResponse(error, verb) {
            // store implementation needed to proceed
        },
        calculateDifference() {
            var startDateTime = new Date(this.updatedEntry.startTime);
            var endDateTime = new Date(this.updatedEntry.endTime);

            // Calculate the difference in milliseconds
            var difference = Math.abs(endDateTime - startDateTime);

            // Convert milliseconds to hours
            var hours = difference / (1000 * 60 * 60);

            return hours;

        }
    }
};

</script>

<style>
.form-data {
    text-align: center;
    padding: 10px;
}
.form-container {

}
</style>