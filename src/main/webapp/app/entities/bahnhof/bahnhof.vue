<template>
  <div>
    <h2 id="page-heading" data-cy="BahnhofHeading">
      <span id="bahnhof-heading">Bahnhofs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'BahnhofCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-bahnhof"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Bahnhof </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && bahnhofs && bahnhofs.length === 0">
      <span>No bahnhofs found</span>
    </div>
    <div class="table-responsive" v-if="bahnhofs && bahnhofs.length > 0">
      <table class="table table-striped" aria-describedby="bahnhofs">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Bahnhofs Nr</span></th>
            <th scope="row"><span>Bahnhofs Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="bahnhof in bahnhofs" :key="bahnhof.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BahnhofView', params: { bahnhofId: bahnhof.id } }">{{ bahnhof.id }}</router-link>
            </td>
            <td>{{ bahnhof.bahnhofsNr }}</td>
            <td>{{ bahnhof.bahnhofsName }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BahnhofView', params: { bahnhofId: bahnhof.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BahnhofEdit', params: { bahnhofId: bahnhof.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(bahnhof)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="acApp.bahnhof.delete.question" data-cy="bahnhofDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-bahnhof-heading">Are you sure you want to delete this Bahnhof?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-bahnhof"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeBahnhof()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./bahnhof.component.ts"></script>
