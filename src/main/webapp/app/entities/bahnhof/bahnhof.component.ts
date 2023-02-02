import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBahnhof } from '@/shared/model/bahnhof.model';

import BahnhofService from './bahnhof.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Bahnhof extends Vue {
  @Inject('bahnhofService') private bahnhofService: () => BahnhofService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public bahnhofs: IBahnhof[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBahnhofs();
  }

  public clear(): void {
    this.retrieveAllBahnhofs();
  }

  public retrieveAllBahnhofs(): void {
    this.isFetching = true;
    this.bahnhofService()
      .retrieve()
      .then(
        res => {
          this.bahnhofs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IBahnhof): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBahnhof(): void {
    this.bahnhofService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Bahnhof is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllBahnhofs();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
