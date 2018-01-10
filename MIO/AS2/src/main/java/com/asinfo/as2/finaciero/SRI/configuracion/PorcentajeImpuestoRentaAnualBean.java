/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual;
/*   8:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioPorcentajeImpuestoRentaAnual;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class PorcentajeImpuestoRentaAnualBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -5297769076253599263L;
/*  28:    */   @EJB
/*  29:    */   private ServicioPorcentajeImpuestoRentaAnual servicioPorcentajeImpuestoRentaAnual;
/*  30:    */   private PorcentajeImpuestoRentaAnual porcentajeImpuestoRentaAnual;
/*  31:    */   private LazyDataModel<PorcentajeImpuestoRentaAnual> listaPorcentajeImpuestoRentaAnual;
/*  32:    */   private DataTable dtPorcentajeImpuestoRentaAnual;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 70 */     this.listaPorcentajeImpuestoRentaAnual = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<PorcentajeImpuestoRentaAnual> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 78 */         List<PorcentajeImpuestoRentaAnual> lista = new ArrayList();
/*  44: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45: 80 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  46: 81 */         lista = PorcentajeImpuestoRentaAnualBean.this.servicioPorcentajeImpuestoRentaAnual.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47: 82 */         PorcentajeImpuestoRentaAnualBean.this.listaPorcentajeImpuestoRentaAnual.setRowCount(PorcentajeImpuestoRentaAnualBean.this.servicioPorcentajeImpuestoRentaAnual.contarPorCriterio(filters));
/*  48:    */         
/*  49: 84 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void crearPorcentajeImpuestoRentaAnual()
/*  55:    */   {
/*  56: 97 */     this.porcentajeImpuestoRentaAnual = new PorcentajeImpuestoRentaAnual();
/*  57: 98 */     this.porcentajeImpuestoRentaAnual.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  58: 99 */     this.porcentajeImpuestoRentaAnual.setIdSucursal(AppUtil.getSucursal().getId());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:112 */     if (getPorcentajeImpuestoRentaAnual().getIdPorcentajeImpuestoRentaAnual() > 0) {
/*  64:113 */       setEditado(true);
/*  65:    */     } else {
/*  66:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:117 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:127 */       this.servicioPorcentajeImpuestoRentaAnual.guardar(this.porcentajeImpuestoRentaAnual);
/*  76:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:129 */       setEditado(false);
/*  78:130 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:135 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:145 */       this.servicioPorcentajeImpuestoRentaAnual.eliminar(this.porcentajeImpuestoRentaAnual);
/*  93:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  98:149 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  99:    */     }
/* 100:151 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String cargarDatos()
/* 104:    */   {
/* 105:160 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:169 */     crearPorcentajeImpuestoRentaAnual();
/* 111:170 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public PorcentajeImpuestoRentaAnual getPorcentajeImpuestoRentaAnual()
/* 115:    */   {
/* 116:183 */     return this.porcentajeImpuestoRentaAnual;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setPorcentajeImpuestoRentaAnual(PorcentajeImpuestoRentaAnual porcentajeImpuestoRentaAnual)
/* 120:    */   {
/* 121:193 */     this.porcentajeImpuestoRentaAnual = porcentajeImpuestoRentaAnual;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LazyDataModel<PorcentajeImpuestoRentaAnual> getListaPorcentajeImpuestoRentaAnual()
/* 125:    */   {
/* 126:202 */     return this.listaPorcentajeImpuestoRentaAnual;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaPorcentajeImpuestoRentaAnual(LazyDataModel<PorcentajeImpuestoRentaAnual> listaPorcentajeImpuestoRentaAnual)
/* 130:    */   {
/* 131:213 */     this.listaPorcentajeImpuestoRentaAnual = listaPorcentajeImpuestoRentaAnual;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DataTable getDtPorcentajeImpuestoRentaAnual()
/* 135:    */   {
/* 136:222 */     return this.dtPorcentajeImpuestoRentaAnual;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDtPorcentajeImpuestoRentaAnual(DataTable dtPorcentajeImpuestoRentaAnual)
/* 140:    */   {
/* 141:232 */     this.dtPorcentajeImpuestoRentaAnual = dtPorcentajeImpuestoRentaAnual;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.PorcentajeImpuestoRentaAnualBean
 * JD-Core Version:    0.7.0.1
 */