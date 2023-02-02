import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Bahnhof = () => import('@/entities/bahnhof/bahnhof.vue');
// prettier-ignore
const BahnhofUpdate = () => import('@/entities/bahnhof/bahnhof-update.vue');
// prettier-ignore
const BahnhofDetails = () => import('@/entities/bahnhof/bahnhof-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'bahnhof',
      name: 'Bahnhof',
      component: Bahnhof,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'bahnhof/new',
      name: 'BahnhofCreate',
      component: BahnhofUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'bahnhof/:bahnhofId/edit',
      name: 'BahnhofEdit',
      component: BahnhofUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'bahnhof/:bahnhofId/view',
      name: 'BahnhofView',
      component: BahnhofDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
