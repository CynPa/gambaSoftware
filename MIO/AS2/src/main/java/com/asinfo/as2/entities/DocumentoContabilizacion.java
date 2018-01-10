/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OrderBy;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="documento_contabilizacion")
/*  26:    */ public class DocumentoContabilizacion
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -4654788611481712034L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="documento_contabilizacion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_contabilizacion")
/*  33:    */   @Column(name="id_documento_contabilizacion")
/*  34:    */   private int idDocumentoContabilizacion;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Enumerated(EnumType.ORDINAL)
/*  40:    */   @Column(name="documento_base", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private DocumentoBase documentoBase;
/*  43:    */   @Enumerated(EnumType.ORDINAL)
/*  44:    */   @Column(name="proceso_contabilizacion", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private ProcesoContabilizacionEnum procesoContabilizacion;
/*  47:    */   @Column(name="debe", nullable=false)
/*  48:    */   private boolean debe;
/*  49:    */   @Column(name="reversa_proceso", nullable=false)
/*  50:    */   private boolean reversaProceso;
/*  51:    */   @Column(name="activo", nullable=false)
/*  52:    */   private boolean activo;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_documento_contabilizacion_padre", nullable=true)
/*  55:    */   private DocumentoContabilizacion documentoContabilizacionPadre;
/*  56:    */   @OneToMany(mappedBy="documentoContabilizacion", fetch=FetchType.LAZY)
/*  57:    */   @OrderBy("orden")
/*  58: 86 */   private List<CriterioContabilizacion> listaCriterioContabilizacion = new ArrayList();
/*  59:    */   @Transient
/*  60:    */   private boolean haber;
/*  61:    */   
/*  62:    */   public int getIdDocumentoContabilizacion()
/*  63:    */   {
/*  64: 99 */     return this.idDocumentoContabilizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdDocumentoContabilizacion(int idDocumentoContabilizacion)
/*  68:    */   {
/*  69:109 */     this.idDocumentoContabilizacion = idDocumentoContabilizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdOrganizacion()
/*  73:    */   {
/*  74:118 */     return this.idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdOrganizacion(int idOrganizacion)
/*  78:    */   {
/*  79:128 */     this.idOrganizacion = idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdSucursal()
/*  83:    */   {
/*  84:137 */     return this.idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdSucursal(int idSucursal)
/*  88:    */   {
/*  89:147 */     this.idSucursal = idSucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isActivo()
/*  93:    */   {
/*  94:156 */     return this.activo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setActivo(boolean activo)
/*  98:    */   {
/*  99:166 */     this.activo = activo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<CriterioContabilizacion> getListaCriterioContabilizacion()
/* 103:    */   {
/* 104:175 */     return this.listaCriterioContabilizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setListaCriterioContabilizacion(List<CriterioContabilizacion> listaCriterioContabilizacion)
/* 108:    */   {
/* 109:185 */     this.listaCriterioContabilizacion = listaCriterioContabilizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public DocumentoBase getDocumentoBase()
/* 113:    */   {
/* 114:194 */     return this.documentoBase;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 118:    */   {
/* 119:204 */     this.documentoBase = documentoBase;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isReversaProceso()
/* 123:    */   {
/* 124:213 */     return this.reversaProceso;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setReversaProceso(boolean reversaProceso)
/* 128:    */   {
/* 129:223 */     this.reversaProceso = reversaProceso;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public DocumentoContabilizacion getDocumentoContabilizacionPadre()
/* 133:    */   {
/* 134:232 */     return this.documentoContabilizacionPadre;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDocumentoContabilizacionPadre(DocumentoContabilizacion documentoContabilizacionPadre)
/* 138:    */   {
/* 139:242 */     this.documentoContabilizacionPadre = documentoContabilizacionPadre;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public ProcesoContabilizacionEnum getProcesoContabilizacion()
/* 143:    */   {
/* 144:251 */     return this.procesoContabilizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setProcesoContabilizacion(ProcesoContabilizacionEnum procesoContabilizacion)
/* 148:    */   {
/* 149:261 */     this.procesoContabilizacion = procesoContabilizacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isDebe()
/* 153:    */   {
/* 154:270 */     return this.debe;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDebe(boolean debe)
/* 158:    */   {
/* 159:280 */     this.debe = debe;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean isHaber()
/* 163:    */   {
/* 164:289 */     return !this.debe;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setHaber(boolean haber)
/* 168:    */   {
/* 169:299 */     this.haber = haber;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getId()
/* 173:    */   {
/* 174:309 */     return this.idDocumentoContabilizacion;
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoContabilizacion
 * JD-Core Version:    0.7.0.1
 */