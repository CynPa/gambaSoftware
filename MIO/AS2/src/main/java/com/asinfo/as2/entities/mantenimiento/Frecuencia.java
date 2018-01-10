/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ import org.hibernate.annotations.ColumnDefault;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="frecuencia", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  22:    */ public class Frecuencia
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="frecuencia", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="frecuencia")
/*  30:    */   @Column(name="id_frecuencia")
/*  31:    */   private int idFrecuencia;
/*  32:    */   @Column(name="id_organizacion")
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", nullable=false, length=20)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=1, max=20)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=100)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=100)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200, nullable=true)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   private boolean predeterminado;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   private boolean activo;
/*  51:    */   @Enumerated(EnumType.ORDINAL)
/*  52:    */   @Column(name="tipo_frecuencia_enum", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private TipoFrecuenciaEnum tipoFrecuenciaEnum;
/*  55:    */   @Enumerated(EnumType.ORDINAL)
/*  56:    */   @Column(name="frecuencia_fecha_enum", nullable=true)
/*  57:    */   private FrecuenciaFechaEnum frecuenciaFechaEnum;
/*  58:    */   @Column(name="indicador_acumulativo", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   @ColumnDefault("'0'")
/*  61:    */   private boolean indicadorAcumulativo;
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 96 */     return this.idFrecuencia;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdFrecuencia()
/*  69:    */   {
/*  70:103 */     return this.idFrecuencia;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdFrecuencia(int idFrecuencia)
/*  74:    */   {
/*  75:111 */     this.idFrecuencia = idFrecuencia;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:118 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:126 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdSucursal()
/*  89:    */   {
/*  90:133 */     return this.idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdSucursal(int idSucursal)
/*  94:    */   {
/*  95:141 */     this.idSucursal = idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCodigo()
/*  99:    */   {
/* 100:148 */     return this.codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setCodigo(String codigo)
/* 104:    */   {
/* 105:156 */     this.codigo = codigo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getNombre()
/* 109:    */   {
/* 110:163 */     return this.nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setNombre(String nombre)
/* 114:    */   {
/* 115:171 */     this.nombre = nombre;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:178 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:186 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isPredeterminado()
/* 129:    */   {
/* 130:193 */     return this.predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPredeterminado(boolean predeterminado)
/* 134:    */   {
/* 135:201 */     this.predeterminado = predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isActivo()
/* 139:    */   {
/* 140:208 */     return this.activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivo(boolean activo)
/* 144:    */   {
/* 145:216 */     this.activo = activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public TipoFrecuenciaEnum getTipoFrecuenciaEnum()
/* 149:    */   {
/* 150:220 */     return this.tipoFrecuenciaEnum;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setTipoFrecuenciaEnum(TipoFrecuenciaEnum tipoFrecuenciaEnum)
/* 154:    */   {
/* 155:224 */     this.tipoFrecuenciaEnum = tipoFrecuenciaEnum;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public FrecuenciaFechaEnum getFrecuenciaFechaEnum()
/* 159:    */   {
/* 160:228 */     return this.frecuenciaFechaEnum;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setFrecuenciaFechaEnum(FrecuenciaFechaEnum frecuenciaFechaEnum)
/* 164:    */   {
/* 165:232 */     this.frecuenciaFechaEnum = frecuenciaFechaEnum;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isIndicadorAcumulativo()
/* 169:    */   {
/* 170:236 */     return this.indicadorAcumulativo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIndicadorAcumulativo(boolean indicadorAcumulativo)
/* 174:    */   {
/* 175:240 */     this.indicadorAcumulativo = indicadorAcumulativo;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.Frecuencia
 * JD-Core Version:    0.7.0.1
 */