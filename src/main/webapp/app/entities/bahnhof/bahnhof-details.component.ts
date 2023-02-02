import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBahnhof } from '@/shared/model/bahnhof.model';
import BahnhofService from './bahnhof.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class BahnhofDetails extends Vue {
  @Inject('bahnhofService') private bahnhofService: () => BahnhofService;
  @Inject('alertService') private alertService: () => AlertService;

  public bahnhof: IBahnhof = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bahnhofId) {
        vm.retrieveBahnhof(to.params.bahnhofId);
      }
    });
  }

  public retrieveBahnhof(bahnhofId) {
    this.bahnhofService()
      .find(bahnhofId)
      .then(res => {
        this.bahnhof = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
