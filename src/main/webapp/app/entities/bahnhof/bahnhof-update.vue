<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="acApp.bahnhof.home.createOrEditLabel" data-cy="BahnhofCreateUpdateHeading">Create or edit a Bahnhof</h2>
        <div>
          <div class="form-group" v-if="bahnhof.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="bahnhof.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bahnhof-bahnhofsNr">Bahnhofs Nr</label>
            <input
              type="number"
              class="form-control"
              name="bahnhofsNr"
              id="bahnhof-bahnhofsNr"
              data-cy="bahnhofsNr"
              :class="{ valid: !$v.bahnhof.bahnhofsNr.$invalid, invalid: $v.bahnhof.bahnhofsNr.$invalid }"
              v-model.number="$v.bahnhof.bahnhofsNr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="bahnhof-bahnhofsName">Bahnhofs Name</label>
            <input
              type="text"
              class="form-control"
              name="bahnhofsName"
              id="bahnhof-bahnhofsName"
              data-cy="bahnhofsName"
              :class="{ valid: !$v.bahnhof.bahnhofsName.$invalid, invalid: $v.bahnhof.bahnhofsName.$invalid }"
              v-model="$v.bahnhof.bahnhofsName.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.bahnhof.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./bahnhof-update.component.ts"></script>
