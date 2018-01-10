/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.PageController;
/*  4:   */ import com.asinfo.as2.entities.CentroCosto;
/*  5:   */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.faces.bean.ManagedBean;
/*  9:   */ import javax.faces.bean.ViewScoped;
/* 10:   */ import org.primefaces.component.datatable.DataTable;
/* 11:   */ 
/* 12:   */ @ManagedBean
/* 13:   */ @ViewScoped
/* 14:   */ public class ListaCentroCostoBean
/* 15:   */   extends PageController
/* 16:   */ {
/* 17:   */   private static final long serialVersionUID = 1L;
/* 18:   */   @EJB
/* 19:   */   private ServicioCentroCosto servicioCentroCosto;
/* 20:   */   private List<CentroCosto> listaCentroCosto;
/* 21:   */   private DataTable dtCentroCosto;
/* 22:   */   
/* 23:   */   public DataTable getDtCentroCosto()
/* 24:   */   {
/* 25:52 */     return this.dtCentroCosto;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setDtCentroCosto(DataTable dtCentroCosto)
/* 29:   */   {
/* 30:60 */     this.dtCentroCosto = dtCentroCosto;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<CentroCosto> getListaCentroCosto()
/* 34:   */   {
/* 35:64 */     if (this.listaCentroCosto == null) {
/* 36:65 */       this.listaCentroCosto = this.servicioCentroCosto.obtenerListaCombo("codigo", true, null);
/* 37:   */     }
/* 38:67 */     return this.listaCentroCosto;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setListaCentroCosto(List<CentroCosto> listaCentroCosto)
/* 42:   */   {
/* 43:71 */     this.listaCentroCosto = listaCentroCosto;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCentroCostoBean
 * JD-Core Version:    0.7.0.1
 */