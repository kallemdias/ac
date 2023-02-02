<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="bahnhof">
        <h2 class="jh-entity-heading" data-cy="bahnhofDetailsHeading"><span>Bahnhof</span> {{ bahnhof.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Bahnhofs Nr</span>
          </dt>
          <dd>
            <span>{{ bahnhof.bahnhofsNr }}</span>
          </dd>
          <dt>
            <span>Bahnhofs Name</span>
          </dt>
          <dd>
            <span>{{ bahnhof.bahnhofsName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="bahnhof.id" :to="{ name: 'BahnhofEdit', params: { bahnhofId: bahnhof.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./bahnhof-details.component.ts"></script>
