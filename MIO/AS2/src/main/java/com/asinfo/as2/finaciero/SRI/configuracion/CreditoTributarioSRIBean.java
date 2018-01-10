/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   6:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.annotation.PostConstruct;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import org.apache.log4j.Logger;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class CreditoTributarioSRIBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -4022967923177689904L;
/*  24:    */   @EJB
/*  25:    */   private transient ServicioCreditoTributario servicioCreditoTributario;
/*  26:    */   private CreditoTributarioSRI creditoTributarioSRI;
/*  27:    */   private LazyDataModel<CreditoTributarioSRI> listaCreditoTributarioSRI;
/*  28:    */   private DataTable dtCreditoTributarioSRI;
/*  29:    */   
/*  30:    */   @PostConstruct
/*  31:    */   public void init()
/*  32:    */   {
/*  33: 68 */     this.listaCreditoTributarioSRI = new LazyDataModel()
/*  34:    */     {
/*  35:    */       private static final long serialVersionUID = -1608862966041385238L;
/*  36:    */       
/*  37:    */       public List<CreditoTributarioSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  38:    */       {
/*  39: 75 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  40: 76 */         List<CreditoTributarioSRI> lista = CreditoTributarioSRIBean.this.servicioCreditoTributario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  41:    */         
/*  42:    */ 
/*  43: 79 */         CreditoTributarioSRIBean.this.listaCreditoTributarioSRI.setRowCount(CreditoTributarioSRIBean.this.servicioCreditoTributario.contarPorCriterio(filters));
/*  44: 80 */         return lista;
/*  45:    */       }
/*  46:    */     };
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void crearEntidad()
/*  50:    */   {
/*  51: 94 */     this.creditoTributarioSRI = new CreditoTributarioSRI();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56:103 */     if (getCreditoTributarioSRI().getIdCreditoTributarioSRI() > 0) {
/*  57:104 */       setEditado(true);
/*  58:    */     } else {
/*  59:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61:108 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:118 */       this.servicioCreditoTributario.guardar(this.creditoTributarioSRI);
/*  69:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  70:120 */       setEditado(false);
/*  71:121 */       limpiar();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:124 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:126 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:136 */       this.servicioCreditoTributario.eliminar(this.creditoTributarioSRI);
/*  86:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  91:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  92:    */     }
/*  93:142 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String cargarDatos()
/*  97:    */   {
/*  98:151 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String limpiar()
/* 102:    */   {
/* 103:160 */     crearEntidad();
/* 104:161 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public CreditoTributarioSRI getCreditoTributarioSRI()
/* 108:    */   {
/* 109:177 */     if (this.creditoTributarioSRI == null) {
/* 110:178 */       this.creditoTributarioSRI = new CreditoTributarioSRI();
/* 111:    */     }
/* 112:180 */     return this.creditoTributarioSRI;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setCreditoTributarioSRI(CreditoTributarioSRI creditoTributarioSRI)
/* 116:    */   {
/* 117:190 */     this.creditoTributarioSRI = creditoTributarioSRI;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public LazyDataModel<CreditoTributarioSRI> getListaCreditoTributarioSRI()
/* 121:    */   {
/* 122:199 */     return this.listaCreditoTributarioSRI;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setListaCreditoTributarioSRI(LazyDataModel<CreditoTributarioSRI> listaCreditoTributarioSRI)
/* 126:    */   {
/* 127:209 */     this.listaCreditoTributarioSRI = listaCreditoTributarioSRI;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public DataTable getDtCreditoTributarioSRI()
/* 131:    */   {
/* 132:218 */     return this.dtCreditoTributarioSRI;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDtCreditoTributarioSRI(DataTable dtCreditoTributarioSRI)
/* 136:    */   {
/* 137:228 */     this.dtCreditoTributarioSRI = dtCreditoTributarioSRI;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.CreditoTributarioSRIBean
 * JD-Core Version:    0.7.0.1
 */