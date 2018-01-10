/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Secuencia;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoContrato;
/*  10:    */ import com.asinfo.as2.enumeraciones.VariablesContrato;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class TipoContratoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 3814949363704819001L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioTipoContrato servicioTipoContrato;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioSecuencia servicioSecuencia;
/*  35:    */   private TipoContrato tipoContrato;
/*  36:    */   private LazyDataModel<TipoContrato> listaTipoContrato;
/*  37:    */   private List<Secuencia> listaSecuencia;
/*  38:    */   private DataTable dtTipoContrato;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 76 */     this.listaTipoContrato = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = -7209780503956649217L;
/*  46:    */       
/*  47:    */       public List<TipoContrato> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 91 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  52: 92 */         List<TipoContrato> lista = TipoContratoBean.this.servicioTipoContrato.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53:    */         
/*  54: 94 */         TipoContratoBean.this.listaTipoContrato.setRowCount(TipoContratoBean.this.servicioTipoContrato.contarPorCriterio(filters));
/*  55:    */         
/*  56: 96 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearEntidad()
/*  62:    */   {
/*  63:110 */     this.tipoContrato = new TipoContrato();
/*  64:111 */     this.tipoContrato.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  65:112 */     this.tipoContrato.setIdSucursal(AppUtil.getSucursal().getId());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:121 */     if ((getTipoContrato() != null) && (getTipoContrato().getIdTipoContrato() != 0))
/*  71:    */     {
/*  72:122 */       this.tipoContrato = this.servicioTipoContrato.cargarDetalle(this.tipoContrato.getIdTipoContrato());
/*  73:123 */       setEditado(true);
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  78:    */     }
/*  79:127 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String guardar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:137 */       this.servicioTipoContrato.guardar(this.tipoContrato);
/*  87:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:139 */       setEditado(false);
/*  89:140 */       limpiar();
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  94:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:    */     }
/*  96:145 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String eliminar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:155 */       this.servicioTipoContrato.eliminar(this.tipoContrato);
/* 104:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 109:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 110:    */     }
/* 111:161 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:170 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String limpiar()
/* 120:    */   {
/* 121:179 */     crearEntidad();
/* 122:180 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public TipoContrato getTipoContrato()
/* 126:    */   {
/* 127:197 */     if (this.tipoContrato == null) {
/* 128:198 */       this.tipoContrato = new TipoContrato();
/* 129:    */     }
/* 130:200 */     return this.tipoContrato;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setTipoContrato(TipoContrato tipoContrato)
/* 134:    */   {
/* 135:210 */     this.tipoContrato = tipoContrato;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public LazyDataModel<TipoContrato> getListaTipoContrato()
/* 139:    */   {
/* 140:219 */     return this.listaTipoContrato;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setListaTipoContrato(LazyDataModel<TipoContrato> listaTipoContrato)
/* 144:    */   {
/* 145:229 */     this.listaTipoContrato = listaTipoContrato;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public DataTable getDtTipoContrato()
/* 149:    */   {
/* 150:238 */     return this.dtTipoContrato;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setDtTipoContrato(DataTable dtTipoContrato)
/* 154:    */   {
/* 155:248 */     this.dtTipoContrato = dtTipoContrato;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<String> getListaVariablesContrato()
/* 159:    */   {
/* 160:257 */     List<String> lista = new ArrayList();
/* 161:258 */     for (VariablesContrato variablesContrato : VariablesContrato.values()) {
/* 162:259 */       lista.add(variablesContrato.getVariable());
/* 163:    */     }
/* 164:262 */     return lista;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Secuencia> getListaSecuencia()
/* 168:    */   {
/* 169:271 */     if (this.listaSecuencia == null) {
/* 170:272 */       this.listaSecuencia = this.servicioSecuencia.obtenerListaCombo();
/* 171:    */     }
/* 172:274 */     return this.listaSecuencia;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.TipoContratoBean
 * JD-Core Version:    0.7.0.1
 */