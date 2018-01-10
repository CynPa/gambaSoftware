/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Temporal;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="turno", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class Turno
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="turno", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="turno")
/*  29:    */   @Column(name="id_turno", unique=true, nullable=false)
/*  30:    */   private int idTurno;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=10)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="nombre", nullable=false, length=50)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", nullable=true, length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Temporal(TemporalType.TIME)
/*  51:    */   @Column(name="hora_entrada", nullable=false)
/*  52:    */   private Date horaEntrada;
/*  53:    */   @Temporal(TemporalType.TIME)
/*  54:    */   @Column(name="hora_salida", nullable=false)
/*  55:    */   private Date horaSalida;
/*  56:    */   @Temporal(TemporalType.TIME)
/*  57:    */   @Column(name="hora_receso_entrada", nullable=true)
/*  58:    */   private Date horaRecesoEntrada;
/*  59:    */   @Temporal(TemporalType.TIME)
/*  60:    */   @Column(name="hora_receso_salida", nullable=true)
/*  61:    */   private Date horaRecesoSalida;
/*  62:    */   @Column(name="indicador_pago_horas_suplementarias", nullable=true)
/*  63:    */   private boolean indicadorPagoHorasSuplementarias;
/*  64:    */   @Transient
/*  65:    */   private boolean indicadorDiaComplementario;
/*  66:    */   
/*  67:    */   public int getId()
/*  68:    */   {
/*  69:102 */     return this.idTurno;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdTurno()
/*  73:    */   {
/*  74:111 */     return this.idTurno;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdTurno(int idTurno)
/*  78:    */   {
/*  79:121 */     this.idTurno = idTurno;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:130 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:140 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:149 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:159 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getCodigo()
/* 103:    */   {
/* 104:168 */     return this.codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setCodigo(String codigo)
/* 108:    */   {
/* 109:178 */     this.codigo = codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:187 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:197 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getDescripcion()
/* 123:    */   {
/* 124:206 */     return this.descripcion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDescripcion(String descripcion)
/* 128:    */   {
/* 129:216 */     this.descripcion = descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isActivo()
/* 133:    */   {
/* 134:225 */     return this.activo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setActivo(boolean activo)
/* 138:    */   {
/* 139:235 */     this.activo = activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isPredeterminado()
/* 143:    */   {
/* 144:244 */     return this.predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPredeterminado(boolean predeterminado)
/* 148:    */   {
/* 149:254 */     this.predeterminado = predeterminado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Date getHoraEntrada()
/* 153:    */   {
/* 154:258 */     return this.horaEntrada;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setHoraEntrada(Date horaEntrada)
/* 158:    */   {
/* 159:262 */     this.horaEntrada = horaEntrada;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Date getHoraSalida()
/* 163:    */   {
/* 164:266 */     return this.horaSalida;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setHoraSalida(Date horaSalida)
/* 168:    */   {
/* 169:270 */     this.horaSalida = horaSalida;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Date getHoraRecesoEntrada()
/* 173:    */   {
/* 174:274 */     return this.horaRecesoEntrada;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setHoraRecesoEntrada(Date horaRecesoEntrada)
/* 178:    */   {
/* 179:278 */     this.horaRecesoEntrada = horaRecesoEntrada;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Date getHoraRecesoSalida()
/* 183:    */   {
/* 184:282 */     return this.horaRecesoSalida;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setHoraRecesoSalida(Date horaRecesoSalida)
/* 188:    */   {
/* 189:286 */     this.horaRecesoSalida = horaRecesoSalida;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String toString()
/* 193:    */   {
/* 194:291 */     return this.nombre;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isIndicadorPagoHorasSuplementarias()
/* 198:    */   {
/* 199:295 */     return this.indicadorPagoHorasSuplementarias;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setIndicadorPagoHorasSuplementarias(boolean indicadorPagoHorasSuplementarias)
/* 203:    */   {
/* 204:299 */     this.indicadorPagoHorasSuplementarias = indicadorPagoHorasSuplementarias;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean isIndicadorDiaComplementario()
/* 208:    */   {
/* 209:303 */     return this.indicadorDiaComplementario;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIndicadorDiaComplementario(boolean indicadorDiaComplementario)
/* 213:    */   {
/* 214:307 */     this.indicadorDiaComplementario = indicadorDiaComplementario;
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.Turno
 * JD-Core Version:    0.7.0.1
 */