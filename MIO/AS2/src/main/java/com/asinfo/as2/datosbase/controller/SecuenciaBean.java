/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Secuencia;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class SecuenciaBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -662109541745938999L;
/*  29:    */   @EJB
/*  30:    */   private ServicioSecuencia servicioSecuencia;
/*  31:    */   private Secuencia secuencia;
/*  32:    */   private LazyDataModel<Secuencia> listaSecuencia;
/*  33:    */   private Integer idSecuencia;
/*  34:    */   private DataTable dtSecuencia;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 64 */     this.listaSecuencia = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<Secuencia> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 71 */         if (SecuenciaBean.this.idSecuencia != null)
/*  46:    */         {
/*  47: 72 */           filters.put("idSecuencia", SecuenciaBean.this.idSecuencia.toString());
/*  48: 73 */           SecuenciaBean.this.idSecuencia = null;
/*  49:    */         }
/*  50: 76 */         List<Secuencia> lista = new ArrayList();
/*  51:    */         
/*  52: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53: 79 */         lista = SecuenciaBean.this.servicioSecuencia.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  54: 80 */         SecuenciaBean.this.listaSecuencia.setRowCount(SecuenciaBean.this.servicioSecuencia.contarPorCriterio(filters));
/*  55:    */         
/*  56: 82 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String guardar()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 94 */       this.servicioSecuencia.guardar(this.secuencia);
/*  66: 95 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  67: 96 */       limpiar();
/*  68: 97 */       setEditado(false);
/*  69:    */     }
/*  70:    */     catch (ExcepcionAS2Financiero e)
/*  71:    */     {
/*  72:101 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  73:102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:106 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:107 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:110 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:120 */       this.servicioSecuencia.eliminar(this.secuencia);
/*  88:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  93:125 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  94:    */     }
/*  95:127 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String editar()
/*  99:    */   {
/* 100:132 */     if (getSecuencia().getId() != 0) {
/* 101:133 */       setEditado(true);
/* 102:    */     } else {
/* 103:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 104:    */     }
/* 105:138 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:143 */     crearSecuencia();
/* 111:144 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:151 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void crearSecuencia()
/* 120:    */   {
/* 121:155 */     this.secuencia = new Secuencia();
/* 122:156 */     this.secuencia.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 123:157 */     this.secuencia.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Secuencia getSecuencia()
/* 127:    */   {
/* 128:166 */     if (this.secuencia == null) {
/* 129:167 */       crearSecuencia();
/* 130:    */     }
/* 131:169 */     return this.secuencia;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setSecuencia(Secuencia secuencia)
/* 135:    */   {
/* 136:179 */     this.secuencia = secuencia;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public LazyDataModel<Secuencia> getListaSecuencia()
/* 140:    */   {
/* 141:189 */     return this.listaSecuencia;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setListaSecuencia(LazyDataModel<Secuencia> listaSecuencia)
/* 145:    */   {
/* 146:200 */     this.listaSecuencia = listaSecuencia;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DataTable getDtSecuencia()
/* 150:    */   {
/* 151:211 */     return this.dtSecuencia;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDtSecuencia(DataTable dtSecuencia)
/* 155:    */   {
/* 156:221 */     this.dtSecuencia = dtSecuencia;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Integer getIdSecuencia()
/* 160:    */   {
/* 161:231 */     return this.idSecuencia;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIdSecuencia(Integer idSecuencia)
/* 165:    */   {
/* 166:241 */     this.idSecuencia = idSecuencia;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.SecuenciaBean
 * JD-Core Version:    0.7.0.1
 */