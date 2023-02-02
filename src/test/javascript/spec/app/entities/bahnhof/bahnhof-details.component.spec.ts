/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BahnhofDetailComponent from '@/entities/bahnhof/bahnhof-details.vue';
import BahnhofClass from '@/entities/bahnhof/bahnhof-details.component';
import BahnhofService from '@/entities/bahnhof/bahnhof.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Bahnhof Management Detail Component', () => {
    let wrapper: Wrapper<BahnhofClass>;
    let comp: BahnhofClass;
    let bahnhofServiceStub: SinonStubbedInstance<BahnhofService>;

    beforeEach(() => {
      bahnhofServiceStub = sinon.createStubInstance<BahnhofService>(BahnhofService);

      wrapper = shallowMount<BahnhofClass>(BahnhofDetailComponent, {
        store,
        localVue,
        router,
        provide: { bahnhofService: () => bahnhofServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBahnhof = { id: 123 };
        bahnhofServiceStub.find.resolves(foundBahnhof);

        // WHEN
        comp.retrieveBahnhof(123);
        await comp.$nextTick();

        // THEN
        expect(comp.bahnhof).toBe(foundBahnhof);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBahnhof = { id: 123 };
        bahnhofServiceStub.find.resolves(foundBahnhof);

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
