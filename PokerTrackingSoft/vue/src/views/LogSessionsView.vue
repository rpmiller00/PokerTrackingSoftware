<template>
    <NavOther class="log-nav" />
    <div class="spacer"></div>
    <div class="content">
        <div class="intro">
            <h1>Please log session below</h1>
        </div>
        <div class="content-form">
            <LogSession />
        </div>
    </div>
</template>

<script>
import LogSession from '../components/LogSession.vue'
import NavOther from '../components/NavOther.vue'
export default {
    components: {
        LogSession,
        NavOther
    }

}
</script>

<style>
.spacer {
    height: 15rem;
}

.intro {
    text-align: center;
}

.content {

    border: 2px solid black;
    margin-left: 25%;
    margin-right: 25%;
}

.log-nav {
    position: absolute;
    top: 0;
    left: 0;
}
</style>