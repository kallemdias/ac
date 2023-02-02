/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BahnhofComponent from '@/entities/bahnhof/bahnhof.vue';
import BahnhofClass from '@/entities/bahnhof/bahnhof.component';
import BahnhofService from '@/entities/bahnhof/bahnhof.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Bahnhof Management Component', () => {
    let wrapper: Wrapper<BahnhofClass>;
    let comp: BahnhofClass;
    let bahnhofServiceStub: SinonStubbedInstance<BahnhofService>;

    beforeEach(() => {
      bahnhofServiceStub = sinon.createStubInstance<BahnhofService>(BahnhofService);
      bahnhofServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BahnhofClass>(BahnhofComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          bahnhofService: () => bahnhofServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      bahnhofServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBahnhofs();
      await comp.$nextTick();

      // THEN
      expect(bahnhofServiceStub.retrieve.called).toBeTruthy();
      expect(comp.bahnhofs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      bahnhofServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(bahnhofServiceStub.retrieve.callCount).toEqual(1);

      comp.removeBahnhof();
      await comp.$nextTick();

      // THEN
      expect(bahnhofServiceStub.delete.called).toBeTruthy();
      expect(bahnhofServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
