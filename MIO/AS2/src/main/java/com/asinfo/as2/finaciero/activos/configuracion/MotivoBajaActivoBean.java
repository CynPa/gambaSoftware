/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.model.LazyDataModel;
/*  25:    */ import org.primefaces.model.SortOrder;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class MotivoBajaActivoBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 9207956156063749356L;
/*  33:    */   @EJB
/*  34:    */   private ServicioMotivoBajaActivo servicioMotivoBajaActivo;
/*  35:    */   @EJB
/*  36:    */   private ServicioDocumento servicioDocumento;
/*  37:    */   private MotivoBajaActivo motivoBajaActivo;
/*  38:    */   private CuentaContable cuentaContable;
/*  39:    */   private LazyDataModel<MotivoBajaActivo> listaMotivoBajaActivo;
/*  40:    */   private List<Documento> listaDocumentoCombo;
/*  41:    */   private DataTable dtMotivoBajaActivo;
/*  42:    */   private DataTable dtCuentaContable;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 83 */     this.listaMotivoBajaActivo = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<MotivoBajaActivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 90 */         List<MotivoBajaActivo> lista = new ArrayList();
/*  54: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55:    */         
/*  56: 93 */         lista = MotivoBajaActivoBean.this.servicioMotivoBajaActivo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57:    */         
/*  58: 95 */         MotivoBajaActivoBean.this.listaMotivoBajaActivo.setRowCount(MotivoBajaActivoBean.this.servicioMotivoBajaActivo.contarPorCriterio(filters));
/*  59:    */         
/*  60: 97 */         return lista;
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void crearMotivoBajaActivo()
/*  66:    */   {
/*  67:110 */     this.motivoBajaActivo = new MotivoBajaActivo();
/*  68:111 */     this.motivoBajaActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  69:112 */     this.motivoBajaActivo.setIdSucursal(AppUtil.getSucursal().getId());
/*  70:113 */     this.motivoBajaActivo.setDocumento(new Documento());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75:122 */     if (getMotivoBajaActivo().getIdMotivoBajaActivo() > 0) {
/*  76:123 */       setEditado(true);
/*  77:    */     } else {
/*  78:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  79:    */     }
/*  80:127 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:137 */       this.servicioMotivoBajaActivo.guardar(this.motivoBajaActivo);
/*  88:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  89:139 */       setEditado(false);
/*  90:140 */       limpiar();
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  95:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:145 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String eliminar()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:155 */       this.servicioMotivoBajaActivo.eliminar(this.motivoBajaActivo);
/* 105:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 110:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 111:    */     }
/* 112:161 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String cargarDatos()
/* 116:    */   {
/* 117:170 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String limpiar()
/* 121:    */   {
/* 122:179 */     crearMotivoBajaActivo();
/* 123:180 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void cargarCuentaContable()
/* 127:    */   {
/* 128:184 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 129:185 */     this.motivoBajaActivo.setCuentaContableMotivoBajaActivo(this.cuentaContable);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public MotivoBajaActivo getMotivoBajaActivo()
/* 133:    */   {
/* 134:202 */     return this.motivoBajaActivo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setMotivoBajaActivo(MotivoBajaActivo motivoBajaActivo)
/* 138:    */   {
/* 139:212 */     this.motivoBajaActivo = motivoBajaActivo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public CuentaContable getCuentaContable()
/* 143:    */   {
/* 144:221 */     return this.cuentaContable;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 148:    */   {
/* 149:231 */     this.cuentaContable = cuentaContable;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LazyDataModel<MotivoBajaActivo> getListaMotivoBajaActivo()
/* 153:    */   {
/* 154:240 */     return this.listaMotivoBajaActivo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaMotivoBajaActivo(LazyDataModel<MotivoBajaActivo> listaMotivoBajaActivo)
/* 158:    */   {
/* 159:250 */     this.listaMotivoBajaActivo = listaMotivoBajaActivo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<Documento> getListaDocumentoCombo()
/* 163:    */   {
/* 164:259 */     if (this.listaDocumentoCombo == null) {
/* 165:    */       try
/* 166:    */       {
/* 167:261 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.MOTIVO_BAJA);
/* 168:    */       }
/* 169:    */       catch (ExcepcionAS2 e)
/* 170:    */       {
/* 171:263 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 172:    */       }
/* 173:    */     }
/* 174:266 */     return this.listaDocumentoCombo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 178:    */   {
/* 179:276 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DataTable getDtMotivoBajaActivo()
/* 183:    */   {
/* 184:285 */     return this.dtMotivoBajaActivo;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setDtMotivoBajaActivo(DataTable dtMotivoBajaActivo)
/* 188:    */   {
/* 189:295 */     this.dtMotivoBajaActivo = dtMotivoBajaActivo;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public DataTable getDtCuentaContable()
/* 193:    */   {
/* 194:304 */     return this.dtCuentaContable;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 198:    */   {
/* 199:314 */     this.dtCuentaContable = dtCuentaContable;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.MotivoBajaActivoBean
 * JD-Core Version:    0.7.0.1
 */