<template>
    <div class="form-container">
        <form v-on:submit.prevent="submitForm">
            <div class="form-data">
                <label for="amount">Amount Won/Lost: $ </label>
                <input type="text" id="amount" v-model="updatedEntry.amount">
            </div>
            <div class="form-data">
                <label for="game-size">Game Size (blinds): </label>
                <input type="text" id="game-size" v-model="updatedEntry.gameSize">
            </div>
            <div class="form-data">
                <label for="game-type">Game Type: </label>
                <input type="text" id="game-type" v-model="updatedEntry.gameType">
            </div>
            <div class="form-data">
                <label for="location">Location: </label>
                <input type="text" id="location" v-model="updatedEntry.location">
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
                location: this.entry.location
            }
        },
    },
    methods: {
        submitForm() {

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
    }
};

</script>

<style>

.form-data {
    text-align: center;
    padding: 10px;
}
</style>