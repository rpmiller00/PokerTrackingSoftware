<template>
    <div class="entry-card">
        <p>Entry #: {{ entry.entryId }}</p>
        <p>Win/Loss: ${{ entry.amount }}</p>
        <p>Stake: {{ entry.gameSize }}</p>
        <p>Game: {{ entry.gameType }}</p>
        <p>Location: {{ entry.location }}</p>

    </div>

</template>

<script>
export default {
    props: {
        entry: {
            type: Object,
            required: true,
        },
    },

};

</script>

<style>
.entry-card {
    border: 2px solid black;
    margin: 10px;
    padding:10px;
    background-color: gray;
}

</style>