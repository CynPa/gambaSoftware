/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   6:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.annotation.PostConstruct;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ import org.primefaces.component.datatable.DataTable;
/*  17:    */ import org.primefaces.model.LazyDataModel;
/*  18:    */ import org.primefaces.model.SortOrder;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class AprobarAnticipoProveedorBean
/*  23:    */   extends PageControllerAS2
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 4068391256345219456L;
/*  26:    */   @EJB
/*  27:    */   private transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  28:    */   private AnticipoProveedor anticipoProveedor;
/*  29:    */   private LazyDataModel<AnticipoProveedor> listaAnticipoProveedor;
/*  30:    */   private DataTable dtAnticipoProveedor;
/*  31:    */   
/*  32:    */   @PostConstruct
/*  33:    */   public void init()
/*  34:    */   {
/*  35: 70 */     this.listaAnticipoProveedor = new LazyDataModel()
/*  36:    */     {
/*  37:    */       private static final long serialVersionUID = -8315201083488588824L;
/*  38:    */       
/*  39:    */       public List<AnticipoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  40:    */       {
/*  41: 82 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  42:    */         
/*  43: 84 */         filters.put("estado", Estado.ELABORADO.toString());
/*  44: 85 */         filters.put("documento.documentoBase", DocumentoBase.ANTICIPO_PROVEEDOR.toString());
/*  45:    */         
/*  46: 87 */         List<AnticipoProveedor> lista = AprobarAnticipoProveedorBean.this.servicioAnticipoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48:    */ 
/*  49: 90 */         AprobarAnticipoProveedorBean.this.listaAnticipoProveedor.setRowCount(AprobarAnticipoProveedorBean.this.servicioAnticipoProveedor.contarPorCriterio(filters));
/*  50: 91 */         return lista;
/*  51:    */       }
/*  52:    */     };
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String crear()
/*  56:    */   {
/*  57:106 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  58:107 */     return "";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:116 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  64:117 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String guardar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:127 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:132 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:142 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  90:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  91:    */     }
/*  92:147 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String aprobarAnticipo()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:161 */       AnticipoProveedor anticipoProveedor = (AnticipoProveedor)this.dtAnticipoProveedor.getRowData();
/* 100:162 */       this.servicioAnticipoProveedor.actualizarEstado(Integer.valueOf(anticipoProveedor.getIdAnticipoProveedor()), Estado.APROBADO);
/* 101:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 106:166 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:169 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:178 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String limpiar()
/* 117:    */   {
/* 118:187 */     crear();
/* 119:188 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public AnticipoProveedor getAnticipoProveedor()
/* 123:    */   {
/* 124:200 */     return this.anticipoProveedor;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 128:    */   {
/* 129:210 */     this.anticipoProveedor = anticipoProveedor;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public LazyDataModel<AnticipoProveedor> getListaAnticipoProveedor()
/* 133:    */   {
/* 134:219 */     return this.listaAnticipoProveedor;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaAnticipoProveedor(LazyDataModel<AnticipoProveedor> listaAnticipoProveedor)
/* 138:    */   {
/* 139:229 */     this.listaAnticipoProveedor = listaAnticipoProveedor;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public DataTable getDtAnticipoProveedor()
/* 143:    */   {
/* 144:238 */     return this.dtAnticipoProveedor;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDtAnticipoProveedor(DataTable dtAnticipoProveedor)
/* 148:    */   {
/* 149:248 */     this.dtAnticipoProveedor = dtAnticipoProveedor;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AprobarAnticipoProveedorBean
 * JD-Core Version:    0.7.0.1
 */