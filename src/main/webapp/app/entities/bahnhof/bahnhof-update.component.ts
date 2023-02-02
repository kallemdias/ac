import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IBahnhof, Bahnhof } from '@/shared/model/bahnhof.model';
import BahnhofService from './bahnhof.service';

const validations: any = {
  bahnhof: {
    bahnhofsNr: {},
    bahnhofsName: {},
  },
};

@Component({
  validations,
})
export default class BahnhofUpdate extends Vue {
  @Inject('bahnhofService') private bahnhofService: () => BahnhofService;
  @Inject('alertService') private alertService: () => AlertService;

  public bahnhof: IBahnhof = new Bahnhof();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bahnhofId) {
        vm.retrieveBahnhof(to.params.bahnhofId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.bahnhof.id) {
      this.bahnhofService()
        .update(this.bahnhof)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Bahnhof is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.bahnhofService()
        .create(this.bahnhof)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Bahnhof is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveBahnhof(bahnhofId): void {
    this.bahnhofService()
      .find(bahnhofId)
      .then(res => {
        this.bahnhof = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
