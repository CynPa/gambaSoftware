/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Pago;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
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
/*  24:    */ public class AprobarPagoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -8338346628015918176L;
/*  28:    */   @EJB
/*  29:    */   private transient ServicioPago servicioPago;
/*  30:    */   private Pago pago;
/*  31:    */   private LazyDataModel<Pago> listaPago;
/*  32:    */   private DataTable dtPago;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 71 */     this.listaPago = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 609994324204466060L;
/*  40:    */       
/*  41:    */       public List<Pago> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  44:    */         
/*  45: 85 */         filters.put("estado", Estado.ELABORADO.toString());
/*  46: 86 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_PROVEEDOR.toString());
/*  47:    */         
/*  48: 88 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  49: 89 */         List<Pago> lista = AprobarPagoBean.this.servicioPago.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         
/*  51: 91 */         AprobarPagoBean.this.listaPago.setRowCount(AprobarPagoBean.this.servicioPago.contarPorCriterio(filters));
/*  52: 92 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String crear()
/*  58:    */   {
/*  59:107 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  60:108 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:117 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  66:118 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:128 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  74:    */       
/*  75:    */ 
/*  76:131 */       limpiar();
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  81:134 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  82:    */     }
/*  83:136 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String eliminar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:147 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:150 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */     }
/*  97:152 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String aprobarPago()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:162 */       Pago pago = (Pago)this.dtPago.getRowData();
/* 105:163 */       this.servicioPago.actualizarEstado(pago.getIdPago(), Estado.APROBADO);
/* 106:    */       
/* 107:165 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 112:168 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 113:    */     }
/* 114:170 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarDatos()
/* 118:    */   {
/* 119:179 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String limpiar()
/* 123:    */   {
/* 124:188 */     crear();
/* 125:189 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Pago getPago()
/* 129:    */   {
/* 130:206 */     return this.pago;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPago(Pago pago)
/* 134:    */   {
/* 135:216 */     this.pago = pago;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public DataTable getDtPago()
/* 139:    */   {
/* 140:225 */     return this.dtPago;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDtPago(DataTable dtPago)
/* 144:    */   {
/* 145:235 */     this.dtPago = dtPago;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public LazyDataModel<Pago> getListaPago()
/* 149:    */   {
/* 150:244 */     return this.listaPago;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaPago(LazyDataModel<Pago> listaPago)
/* 154:    */   {
/* 155:254 */     this.listaPago = listaPago;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AprobarPagoBean
 * JD-Core Version:    0.7.0.1
 */