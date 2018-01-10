/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   6:    */ import com.asinfo.as2.entities.Secuencia;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="autorizacion_documento_SRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_documento", "id_secuencia"})})
/*  22:    */ public class AutorizacionDocumentoSRI
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="autorizacion_documento_SRI", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_documento_SRI")
/*  29:    */   @Column(name="id_autorizacion_documento_SRI")
/*  30:    */   private int idAutorizacionDocumentoSRI;
/*  31:    */   @ManyToOne
/*  32:    */   @JoinColumn(name="id_documento", nullable=true)
/*  33:    */   private Documento documento;
/*  34:    */   @ManyToOne
/*  35:    */   @JoinColumn(name="id_punto_de_venta", nullable=true)
/*  36:    */   private PuntoDeVenta puntoDeVenta;
/*  37:    */   @ManyToOne
/*  38:    */   @JoinColumn(name="id_secuencia", nullable=true)
/*  39:    */   private Secuencia secuencia;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   private int idSucursal;
/*  44:    */   @Column(name="autorizacion", length=10, nullable=false)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=10, max=37)
/*  47:    */   private String autorizacion;
/*  48:    */   @Column(name="activo", nullable=false)
/*  49:    */   private boolean activo;
/*  50:    */   @Column(name="predeterminado", nullable=false)
/*  51:    */   private boolean predeterminado;
/*  52:    */   @Transient
/*  53:    */   private boolean seleccionado;
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 85 */     return this.idAutorizacionDocumentoSRI;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toString()
/*  61:    */   {
/*  62: 90 */     return this.autorizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdAutorizacionDocumentoSRI()
/*  66:    */   {
/*  67: 99 */     return this.idAutorizacionDocumentoSRI;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdAutorizacionDocumentoSRI(int idAutorizacionDocumentoSRI)
/*  71:    */   {
/*  72:109 */     this.idAutorizacionDocumentoSRI = idAutorizacionDocumentoSRI;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Documento getDocumento()
/*  76:    */   {
/*  77:118 */     return this.documento;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setDocumento(Documento documento)
/*  81:    */   {
/*  82:128 */     this.documento = documento;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:137 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:147 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:156 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:166 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getAutorizacion()
/* 106:    */   {
/* 107:175 */     return this.autorizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setAutorizacion(String autorizacion)
/* 111:    */   {
/* 112:185 */     this.autorizacion = autorizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isActivo()
/* 116:    */   {
/* 117:194 */     return this.activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setActivo(boolean activo)
/* 121:    */   {
/* 122:204 */     this.activo = activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isPredeterminado()
/* 126:    */   {
/* 127:213 */     return this.predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPredeterminado(boolean predeterminado)
/* 131:    */   {
/* 132:223 */     this.predeterminado = predeterminado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Secuencia getSecuencia()
/* 136:    */   {
/* 137:232 */     return this.secuencia;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setSecuencia(Secuencia secuencia)
/* 141:    */   {
/* 142:242 */     this.secuencia = secuencia;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public PuntoDeVenta getPuntoDeVenta()
/* 146:    */   {
/* 147:251 */     return this.puntoDeVenta;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 151:    */   {
/* 152:261 */     this.puntoDeVenta = puntoDeVenta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isSeleccionado()
/* 156:    */   {
/* 157:265 */     return this.seleccionado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setSeleccionado(boolean seleccionado)
/* 161:    */   {
/* 162:269 */     this.seleccionado = seleccionado;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI
 * JD-Core Version:    0.7.0.1
 */