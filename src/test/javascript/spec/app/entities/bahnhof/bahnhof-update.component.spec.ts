/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BahnhofUpdateComponent from '@/entities/bahnhof/bahnhof-update.vue';
import BahnhofClass from '@/entities/bahnhof/bahnhof-update.component';
import BahnhofService from '@/entities/bahnhof/bahnhof.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Bahnhof Management Update Component', () => {
    let wrapper: Wrapper<BahnhofClass>;
    let comp: BahnhofClass;
    let bahnhofServiceStub: SinonStubbedInstance<BahnhofService>;

    beforeEach(() => {
      bahnhofServiceStub = sinon.createStubInstance<BahnhofService>(BahnhofService);

      wrapper = shallowMount<BahnhofClass>(BahnhofUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          bahnhofService: () => bahnhofServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.bahnhof = entity;
        bahnhofServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bahnhofServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.bahnhof = entity;
        bahnhofServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bahnhofServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBahnhof = { id: 123 };
        bahnhofServiceStub.find.resolves(foundBahnhof);
        bahnhofServiceStub.retrieve.resolves([foundBahnhof]);

        // WHEN
        comp.beforeRouteEnter({ params: { bahnhofId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.bahnhof).toBe(foundBahnhof);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
