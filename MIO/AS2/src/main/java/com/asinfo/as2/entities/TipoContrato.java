/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="tipo_contrato", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  18:    */ public class TipoContrato
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -4094069872938871703L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="tipo_contrato", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_contrato")
/*  25:    */   @Column(name="id_tipo_contrato", unique=true, nullable=false)
/*  26:    */   private int idTipoContrato;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", length=200)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @Column(name="texto_contrato", nullable=true, columnDefinition="text")
/*  47:    */   private String textoContrato;
/*  48:    */   @Column(name="contrato_eventual", nullable=false)
/*  49:    */   private boolean contratoEventual;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_secuencia", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Secuencia secuencia;
/*  54:    */   
/*  55:    */   public TipoContrato() {}
/*  56:    */   
/*  57:    */   public TipoContrato(int idTipoContrato, String codigo, String nombre)
/*  58:    */   {
/*  59: 97 */     this.idTipoContrato = idTipoContrato;
/*  60: 98 */     this.codigo = codigo;
/*  61: 99 */     this.nombre = nombre;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getId()
/*  65:    */   {
/*  66:109 */     return this.idTipoContrato;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdTipoContrato()
/*  70:    */   {
/*  71:118 */     return this.idTipoContrato;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdTipoContrato(int idTipoContrato)
/*  75:    */   {
/*  76:128 */     this.idTipoContrato = idTipoContrato;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdOrganizacion()
/*  80:    */   {
/*  81:137 */     return this.idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdOrganizacion(int idOrganizacion)
/*  85:    */   {
/*  86:147 */     this.idOrganizacion = idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:156 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:166 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getCodigo()
/* 100:    */   {
/* 101:175 */     return this.codigo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCodigo(String codigo)
/* 105:    */   {
/* 106:185 */     this.codigo = codigo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getNombre()
/* 110:    */   {
/* 111:194 */     return this.nombre;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setNombre(String nombre)
/* 115:    */   {
/* 116:204 */     this.nombre = nombre;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getDescripcion()
/* 120:    */   {
/* 121:213 */     return this.descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setDescripcion(String descripcion)
/* 125:    */   {
/* 126:223 */     this.descripcion = descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isActivo()
/* 130:    */   {
/* 131:232 */     return this.activo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setActivo(boolean activo)
/* 135:    */   {
/* 136:242 */     this.activo = activo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isPredeterminado()
/* 140:    */   {
/* 141:251 */     return this.predeterminado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPredeterminado(boolean predeterminado)
/* 145:    */   {
/* 146:261 */     this.predeterminado = predeterminado;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getTextoContrato()
/* 150:    */   {
/* 151:270 */     return this.textoContrato;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setTextoContrato(String textoContrato)
/* 155:    */   {
/* 156:280 */     this.textoContrato = textoContrato;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isContratoEventual()
/* 160:    */   {
/* 161:287 */     return this.contratoEventual;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setContratoEventual(boolean contratoEventual)
/* 165:    */   {
/* 166:294 */     this.contratoEventual = contratoEventual;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Secuencia getSecuencia()
/* 170:    */   {
/* 171:303 */     return this.secuencia;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setSecuencia(Secuencia secuencia)
/* 175:    */   {
/* 176:313 */     this.secuencia = secuencia;
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoContrato
 * JD-Core Version:    0.7.0.1
 */